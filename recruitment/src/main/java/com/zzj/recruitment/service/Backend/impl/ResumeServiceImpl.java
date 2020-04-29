package com.zzj.recruitment.service.Backend.impl;

import com.google.common.collect.Lists;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.dao.*;
import com.zzj.recruitment.pojo.Industryset;
import com.zzj.recruitment.pojo.PositionTypeSet;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.service.Backend.IResumeService;
import com.zzj.recruitment.service.IFileService;
import com.zzj.recruitment.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-17 21:40:29
 */
@Service("resumeService")
@Slf4j
public class ResumeServiceImpl implements IResumeService {


    @Autowired
    ResumeMapper resumeMapper;

    @Autowired
    PositionTypeSetMapper positionTypeSetMapper;

    @Autowired
    IndustrysetMapper industrysetMapper;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    WorkSkilltagMapper workSkilltagMapper;

    @Autowired
    SchoolMapper schoolMapper;

    @Autowired
    ResumeCertificateMapper resumeCertificateMapper;

    @Autowired
    CredentialMapper credentialMapper;

    @Autowired
    workExpMapper workExpMapper;

    @Autowired
    projectExpMapper projectExpMapper;


    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private IFileService fileService;

    /**
     * 获取在线简历信息
     *
     * @param user
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<ResumeInfoVo> returnResumeInfo(User user) {
        ResumeInfoVo resumeInfoVo = (ResumeInfoVo) redisTemplate.opsForValue()
                .get(Const.RESUME_INFO + user.getId());
        if (resumeInfoVo != null) {
            redisTemplate.expire(Const.RESUME_INFO + user.getId(), 60 * 60, TimeUnit.SECONDS);
            return ServerResponse.createResponseBySuccess("获取简历信息成功!", resumeInfoVo);
        }
        // 获取简历基本信息
        ResumeBaseInfoVo baseInfo = resumeMapper.selectBaseInfoByUserId(user.getId());
        if (baseInfo == null) {
            return ServerResponse.createResponseByErrorMsg("获取简历信息失败，请联系管理员！");
        }
        baseInfo.setTelephone(user.getTelephone());
        baseInfo.setEmail(user.getEmail());
        // 简历的id
        Integer resumeId = baseInfo.getId();
        // 根据简历Id找到期望的行业
        List<Map<String, Object>> industries = resumeMapper.selectIndustriesByResumeId(resumeId);
        if (industries == null) {
            return ServerResponse.createResponseByErrorMsg("获取简历信息失败，请联系管理员！");
        }
        baseInfo.setIndustries(industries);
        // 需要判断期望的行业数量是否为0
        if (industries.size() != 0) {
            StringBuffer industryDesc = new StringBuffer();
            for (Map<String, Object> industry : industries) {
                industryDesc.append(industry.get("industryName") + "·");
            }
            int index = industryDesc.lastIndexOf("·");
            industryDesc.delete(index, index + 1);
            baseInfo.setIndustryDesc(new String(industryDesc));
        }
        // 根据简历Id查找工作经历
        List<ResumeWorkExpListVo> workExpListVos = resumeMapper.selectWorkExpByResumeId(resumeId);
        if (workExpListVos == null) {
            return ServerResponse.createResponseByErrorMsg("获取简历信息失败，请联系管理员！");
        }
        // 判断有无工作经历
        if (workExpListVos.size() != 0) {
            // 根据工作经历Id查找到技能标签
            for (ResumeWorkExpListVo workExpListVo : workExpListVos) {
                List<Map<String,Object>> skillTags = resumeMapper.selectSkillTagByWid(workExpListVo.getId());
                if (skillTags == null) {
                    return ServerResponse.createResponseByErrorMsg("获取简历信息失败，请联系管理员！");
                }
                workExpListVo.setSkillTags(skillTags);
            }
        }
        // 根据简历Id查找教育经历
        List<ResumeEducationExpListVo> educationExpListVos = resumeMapper.selectEducationExpByResumeId(resumeId);
        if (educationExpListVos == null) {
            return ServerResponse.createResponseByErrorMsg("获取简历信息失败，请联系管理员！");
        }
        // 根据简历Id查找项目经历
        List<ResumeProjectExpListVo> projectExpListVos = resumeMapper.selectProjectExpByResumeId(resumeId);
        if (projectExpListVos == null) {
            return ServerResponse.createResponseByErrorMsg("获取简历信息失败，请联系管理员！");
        }
        // 根据简历Id找到资格证书
        List<ResumeCertificationListVo> certificationListVos = resumeMapper.selectCertificationByResumeId(resumeId);
        if (certificationListVos == null) {
            return ServerResponse.createResponseByErrorMsg("获取简历信息失败，请联系管理员！");
        }
        resumeInfoVo = new ResumeInfoVo(baseInfo, workExpListVos, projectExpListVos
                , educationExpListVos, certificationListVos);
        redisTemplate.opsForValue().set(Const.RESUME_INFO+user.getId(), resumeInfoVo, 60*30, TimeUnit.SECONDS);
        return ServerResponse.createResponseBySuccess("获取简历信息成功!", resumeInfoVo);
    }

    /**
     * 编辑在线简历基本信息
     *
     * @param resume
     * @param user
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<ResumeBaseInfoVo> updateResumeBaseInfo(UpdateResumeBaseInfoVo resume, User user) {
        boolean flag = false;
        // 自动填充简历的所属用户
        resume.setRuserid(user.getId());
        // 根据用户Id查找对应的简历Id
        Integer resumeId = resumeMapper.selectResumeIdByUserId(user.getId());
        if (resumeId != null && resume.getId() != null & !resume.getId().equals(resumeId)) {
            return ServerResponse.createResponseByErrorMsg("请确认需要修改的简历信息为您本人的！");
        } else if (resumeId == null) {
            // 万一找不到该用户的在线简历，则进行插入操作
            resume.setRuserid(user.getId());
            Integer result = resumeMapper.insertReumeBaseInfo(resume);
            if (result > 0) {
                flag = true;
            }
        } else {
            // 已经存在，则进行更新
            resume.setId(resumeId);
            Integer result = resumeMapper.updateResumeBaseInfo(resume);
            if (result > 0) {
                flag = true;
            }
        }
        // 根据简历Id修改简历
        if (flag) {
            this.assembleResumeBaseInfo(resume);
            // 获取简历基本信息
            ResumeBaseInfoVo baseInfo = resumeMapper.selectBaseInfoByUserId(user.getId());
            if (baseInfo == null) {
                return ServerResponse.createResponseByErrorMsg("获取简历信息失败，请联系管理员！");
            }
            baseInfo.setTelephone(user.getTelephone());
            baseInfo.setEmail(user.getEmail());
            // 简历的id
            resumeId = baseInfo.getId();
            // 根据简历Id找到期望的行业
            List<Map<String, Object>> industries = resumeMapper.selectIndustriesByResumeId(resumeId);
            if (industries == null) {
                return ServerResponse.createResponseByErrorMsg("获取简历信息失败，请联系管理员！");
            }
            baseInfo.setIndustries(industries);
            // 需要判断期望的行业数量是否为0
            if (industries.size() != 0) {
                StringBuffer industryDesc = new StringBuffer();
                for (Map<String, Object> industry : industries) {
                    industryDesc.append(industry.get("industryName") + "·");
                }
                int index = industryDesc.lastIndexOf("·");
                industryDesc.delete(index, index + 1);
                baseInfo.setIndustryDesc(new String(industryDesc));
            }
            // 如果插入/修改成功，更新缓存中的数据
            ResumeInfoVo resumeInfoVo = (ResumeInfoVo) redisTemplate.opsForValue().get(Const.RESUME_INFO + user.getId());
//            ResumeBaseInfoVo baseInfoVo = this.assembleResumeBaseInfo(resume, baseInfo);
//            baseInfoVo.setTelephone(user.getTelephone());
//            baseInfoVo.setEmail(user.getEmail());
            resumeInfoVo.setResumeBaseInfoVo(baseInfo);
            // 更新缓存中的数据
            redisTemplate.opsForValue().set(Const.RESUME_INFO + user.getId(), resumeInfoVo, 60 * 30, TimeUnit.SECONDS);
            return ServerResponse.createResponseBySuccess("修改基本信息成功！", baseInfo);
        }
        return ServerResponse.createResponseByErrorMsg("修改失败！");
    }

    /**
     * 生成简历基本信息
     * 构造 List<Map<String, Object>> industries; 并且更新resume industry关系表
     *
     * @param updateResumeBaseInfoVo
     * @return
     */
    private ResumeBaseInfoVo assembleResumeBaseInfo(UpdateResumeBaseInfoVo updateResumeBaseInfoVo) {
        ResumeBaseInfoVo baseInfoVo = new ResumeBaseInfoVo();
        Integer resumeId = updateResumeBaseInfoVo.getId();
        baseInfoVo.setId(resumeId);
        baseInfoVo.setHeadermask(updateResumeBaseInfoVo.getHeadermask());
        baseInfoVo.setRealname(updateResumeBaseInfoVo.getRealname());
        baseInfoVo.setGender(updateResumeBaseInfoVo.getGender());
        baseInfoVo.setBirthday(updateResumeBaseInfoVo.getBirthday());
        baseInfoVo.setCurrentstatus(updateResumeBaseInfoVo.getCurrentstatus());
        baseInfoVo.setAdvantage(updateResumeBaseInfoVo.getAdvantage());
        baseInfoVo.setSocialpage(updateResumeBaseInfoVo.getSocialpage());
        // 删除Otherattachment附件
        if (updateResumeBaseInfoVo.getOtherattachment() == null) {
            Boolean aBoolean = fileService.delete(baseInfoVo.getOtherattachment(), "attachment");
            log.info("删除删除Otherattachment附件结果：", aBoolean);
        }
        baseInfoVo.setOtherattachment(updateResumeBaseInfoVo.getOtherattachment());
        // 删除Resumeattachment附件
        if (updateResumeBaseInfoVo.getResumeattachment() == null) {
            Boolean aBoolean = fileService.delete(baseInfoVo.getResumeattachment(), "attachment");
            log.info("删除Resumeattachment附件：", aBoolean);
        }
        baseInfoVo.setResumeattachment(updateResumeBaseInfoVo.getResumeattachment());
        // 职位类型Id
        Integer expectPositionId = updateResumeBaseInfoVo.getExpectpositionid();
        if (expectPositionId != null) {
            baseInfoVo.setExpectpositionId(expectPositionId);
            String ptName = positionTypeSetMapper.selectByPrimaryKey(expectPositionId).getPtname();
            baseInfoVo.setExpectposition(ptName);
        }
        baseInfoVo.setLowsalary(updateResumeBaseInfoVo.getLowsalary());
        baseInfoVo.setHighsalary(updateResumeBaseInfoVo.getHighsalary());
        // 构造 List<Map<String, Object>> industries; 并且更新resume industry关系表
        // 1.删除该用户的关系
        Integer result = resumeMapper.deleteResumeIndustryByRid(resumeId);
        if (result > 0) {
            log.info("删除简历Id：" + resumeId + "数据库，用户：" + updateResumeBaseInfoVo.getRealname() + "成功!");
        }
        // 2.重新插入数据
        List<Map<String, Object>> industries = new ArrayList<>();
        StringBuffer industryDesc = new StringBuffer();
        //  遍历新industry数据
        if (updateResumeBaseInfoVo.getIndustryid() != null && updateResumeBaseInfoVo.getIndustryid().size() > 0) {
            for (Integer industryId : updateResumeBaseInfoVo.getIndustryid()) {
                //  获取新industry实体数据
                Industryset industrySet = industrysetMapper.selectByPrimaryKey(industryId);
                //  插入数据库
                result = resumeMapper.insertResumeIndusty(resumeId, industrySet.getId());
                if (result > 0) {
                    log.info("插入简历Id：" + resumeId + "数据库，用户：" + updateResumeBaseInfoVo.getRealname() + "成功!");
                }
                industryDesc.append(industrySet.getIname() + "·");
                Map<String, Object> map = new HashMap<>();
                map.put("industryId", industrySet.getId());
                map.put("industryName", industrySet.getIname());
                industries.add(map);
            }
            int index = industryDesc.lastIndexOf("·");
            industryDesc.delete(index, index + 1);
            baseInfoVo.setIndustries(industries);
            baseInfoVo.setIndustryDesc(new String(industryDesc));
        }
        if (updateResumeBaseInfoVo.getCityid() != null && updateResumeBaseInfoVo.getCityid() != "" && updateResumeBaseInfoVo.getCityid().length() > 0) {
            String cityName = cityMapper.selectByPrimaryKey(updateResumeBaseInfoVo.getCityid()).getCityname();
            baseInfoVo.setCity(cityName);
        }
        return baseInfoVo;
    }


    /**
     * 更新/添加简历工作经历信息
     *
     * @param workExpId
     * @param updateResumeWorkExpVo
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<ResumeWorkExpListVo> updateResumeWorkExpInfo(Integer workExpId,
                                                                       UpdateResumeWorkExpVo updateResumeWorkExpVo,
                                                                       User user) {
        boolean flag = false;
        Integer result = null;
        ResumeWorkExpListVo resumeWorkExpListVo = null;
        ResumeInfoVo resumeInfoVo = (ResumeInfoVo) redisTemplate.opsForValue()
                .get(Const.RESUME_INFO + user.getId());
        List<ResumeWorkExpListVo> workExpListVos = resumeInfoVo.getWorkExpListVos();

        // 工作经历所属简历Id
        Integer resumeId = resumeMapper.selectResumeIdByUserId(user.getId());
        updateResumeWorkExpVo.setResuid(resumeId);

        // 如果workExpId存在，则说明是更新
        if (workExpId != null) {
            // 设置工作经历id
            updateResumeWorkExpVo.setId(workExpId);
            // 更新数据库
            result = resumeMapper.updateResumeWorkExpById(updateResumeWorkExpVo);
            if (result > 0) {
                flag = true;
                // 更新缓存
                // 遍历工作经历，删除对应id的工作经历
                Iterator<ResumeWorkExpListVo> iterator = workExpListVos.iterator();
                while (iterator.hasNext()) {
                    ResumeWorkExpListVo value = iterator.next();
                    if (value.getId().equals(workExpId)) {
                        iterator.remove();
                    }
                }
            }
        } else {
            // 如果workExpId为空，则说明是新增
            result = resumeMapper.insertResumeWorkExp(updateResumeWorkExpVo);
            if (result != null) {
                flag = true;
            }
        }

        if (flag) {
            // 将工作经历转换格式，添加进缓存
            resumeWorkExpListVo = this.assembleResumeWorkExpInfo(updateResumeWorkExpVo);
            workExpListVos.add(resumeWorkExpListVo);
            resumeInfoVo.setWorkExpListVos(workExpListVos);
            redisTemplate.opsForValue().set(Const.RESUME_INFO + user.getId(),
                    resumeInfoVo, 60 * 30, TimeUnit.SECONDS);
            return ServerResponse.createResponseBySuccess("更新工作经历成功！",
                    resumeWorkExpListVo);
        }
        return ServerResponse.createResponseByErrorMsg("更新工作经历失败，请联系管理员！");
    }

    @Override
    public ServerResponse deleteResumeWorkExpInfo(Integer workExpId, User user) {
        // 根据用户Id查找对应的简历Id
        Integer resumeId = resumeMapper.selectResumeIdByUserId(user.getId());
        Integer result = workExpMapper.selectCountByWidRid(workExpId, resumeId);
        if (result > 0) {
            int i = workExpMapper.deleteByPrimaryKey(workExpId);
            if (i > 0) {
                // 更新缓存
                ResumeInfoVo resumeInfoVo = (ResumeInfoVo) redisTemplate.opsForValue()
                        .get(Const.RESUME_INFO + user.getId());
                List<ResumeWorkExpListVo> workExpListVos = resumeInfoVo.getWorkExpListVos();
                Iterator<ResumeWorkExpListVo> iterator = workExpListVos.iterator();
                while (iterator.hasNext()) {
                    ResumeWorkExpListVo next = iterator.next();
                    if (next.getId().equals(workExpId)) {
                        iterator.remove();
                    }
                }
                resumeInfoVo.setWorkExpListVos(workExpListVos);
                redisTemplate.opsForValue().set(Const.RESUME_INFO + user.getId(), resumeInfoVo, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
                return ServerResponse.createResponseBySuccess("删除成功！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("删除失败，请联系管理员");
    }

    /**
     * 生成简历工作经历信息
     *
     * @param updateResumeWorkExpVo
     * @return
     */
    private ResumeWorkExpListVo assembleResumeWorkExpInfo(UpdateResumeWorkExpVo updateResumeWorkExpVo) {
        ResumeWorkExpListVo resumeWorkExpListVo = new ResumeWorkExpListVo();
        resumeWorkExpListVo.setId(updateResumeWorkExpVo.getId());
        resumeWorkExpListVo.setCname(updateResumeWorkExpVo.getCname());
        resumeWorkExpListVo.setIndustryId(updateResumeWorkExpVo.getIndustryId());
        resumeWorkExpListVo.setIndustryName(industrysetMapper.selectByPrimaryKey(updateResumeWorkExpVo.getIndustryId()).getIname());
        resumeWorkExpListVo.setDepartment(updateResumeWorkExpVo.getDepartment());
        resumeWorkExpListVo.setWorkcontent(updateResumeWorkExpVo.getWorkcontent());
        resumeWorkExpListVo.setPosition(updateResumeWorkExpVo.getPosition());
        resumeWorkExpListVo.setPositionId(updateResumeWorkExpVo.getPositionId());
        // 保存skilltags
        //  先删除该用户原来的skilltag
        workSkilltagMapper.deleteByWid(updateResumeWorkExpVo.getId());
        // 将新的技能标签插入数据库，并转换成文字
        List<Integer> skillTags = updateResumeWorkExpVo.getSkillTags();
        if (skillTags.size() > 0) {
            workSkilltagMapper.batchInsert(skillTags, updateResumeWorkExpVo.getId());
            List<Map<String,Object>> skillTagsStr = Lists.newArrayList();
            for (Integer skillTag : skillTags) {
                PositionTypeSet pt = positionTypeSetMapper.selectByPrimaryKey(skillTag);
                Map<String,Object> map = new HashMap<>();
                map.put("id", pt.getId());
                map.put("skillTag", pt.getPtname());
                skillTagsStr.add(map);
            }
            // 放进新的工作经历中
            resumeWorkExpListVo.setSkillTags(skillTagsStr);
        }
        resumeWorkExpListVo.setStartdate(updateResumeWorkExpVo.getStartdate());
        resumeWorkExpListVo.setEnddate(updateResumeWorkExpVo.getEnddate());
        return resumeWorkExpListVo;
    }


    /**
     * 添加/更新简历项目经历信息
     * projectExpId存在则更新，否则新增
     * @param projectExpId
     * @param updateResumeProjectExpVo
     * @param user
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<ResumeProjectExpListVo> updateResumeProjectExpInfo(Integer projectExpId, UpdateResumeProjectExpVo updateResumeProjectExpVo, User user) {
        boolean flag = false;
        Integer result = null;
        ResumeProjectExpListVo resumeProjectExpListVo= null;
        ResumeInfoVo resumeInfoVo = (ResumeInfoVo) redisTemplate.opsForValue().get(Const.RESUME_INFO + user.getId());
        List<ResumeProjectExpListVo> projectExpListVos = resumeInfoVo.getProjectExpListVos();

        // 项目经历所属简历Id
        Integer resumeId = resumeMapper.selectResumeIdByUserId(user.getId());
        updateResumeProjectExpVo.setResuid(resumeId);

        if (projectExpId != null) {
            // 更新项目经历
            // 设置项目经历id
            updateResumeProjectExpVo.setId(projectExpId);
            // 更新数据库
            result = resumeMapper.updateResumeProjectExpById(updateResumeProjectExpVo);
            if (result > 0) {
                flag = true;
                // 更新缓存
                // 遍历项目经历，删除对应id的工作经历
                Iterator<ResumeProjectExpListVo> iterator = projectExpListVos.iterator();
                while (iterator.hasNext()) {
                    ResumeProjectExpListVo value = iterator.next();
                    if (value.getId().equals(projectExpId)) {
                        iterator.remove();
                    }
                }
            }
        } else {
            // 新增项目经历
            result = resumeMapper.insertResumeProjectExp(updateResumeProjectExpVo);
            if (result != null) {
                flag = true;
            }
        }

        if (flag) {
            // 将工作经历转换格式，添加进缓存
            resumeProjectExpListVo = this.assembleResumeProjectExpInfo(updateResumeProjectExpVo);
            projectExpListVos.add(resumeProjectExpListVo);
            resumeInfoVo.setProjectExpListVos(projectExpListVos);
            redisTemplate.opsForValue().set(Const.RESUME_INFO + user.getId(), resumeInfoVo, 60 * 30, TimeUnit.SECONDS);
            return ServerResponse.createResponseBySuccess("更新项目经历成功！", resumeProjectExpListVo);
        }
        return ServerResponse.createResponseByErrorMsg("更新项目经历失败，请联系管理员！");
    }

    @Override
    public ServerResponse deleteResumeProjecExpInfo(Integer projectExpId, User user) {
        // 根据用户Id查找对应的简历Id
        Integer resumeId = resumeMapper.selectResumeIdByUserId(user.getId());
        Integer result = projectExpMapper.selectCountByPidRid(projectExpId, resumeId);
        if (result > 0) {
            int i = projectExpMapper.deleteByPrimaryKey(projectExpId);
            if (i > 0) {
                // 更新缓存
                ResumeInfoVo resumeInfoVo = (ResumeInfoVo) redisTemplate.opsForValue()
                        .get(Const.RESUME_INFO + user.getId());
                List<ResumeProjectExpListVo> projectExpListVo = resumeInfoVo.getProjectExpListVos();
                Iterator<ResumeProjectExpListVo> iterator = projectExpListVo.iterator();
                while (iterator.hasNext()) {
                    ResumeProjectExpListVo next = iterator.next();
                    if (next.getId().equals(projectExpId)) {
                        iterator.remove();
                    }
                }
                resumeInfoVo.setProjectExpListVos(projectExpListVo);
                redisTemplate.opsForValue().set(Const.RESUME_INFO + user.getId(), resumeInfoVo, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
                return ServerResponse.createResponseBySuccess("删除成功！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("删除失败，请联系管理员");
    }

    /**
     * 生成项目经历信息
     * @param updateResumeProjectExpVo
     * @return
     */
    private ResumeProjectExpListVo assembleResumeProjectExpInfo(UpdateResumeProjectExpVo updateResumeProjectExpVo) {
        ResumeProjectExpListVo resumeProjectExpListVo = new ResumeProjectExpListVo();
        resumeProjectExpListVo.setId(updateResumeProjectExpVo.getId());
        resumeProjectExpListVo.setProjectName(updateResumeProjectExpVo.getProjectName());
        resumeProjectExpListVo.setRole(updateResumeProjectExpVo.getRole());
        resumeProjectExpListVo.setStarttime(updateResumeProjectExpVo.getStarttime());
        resumeProjectExpListVo.setEndtime(updateResumeProjectExpVo.getEndtime());
        resumeProjectExpListVo.setDescription(updateResumeProjectExpVo.getDescription());
        return resumeProjectExpListVo;
    }

    /**
     * 添加/更新简历教育经历信息
     * educationExpId存在则更新，否则新增
     *
     * @param educationExpId
     * @param updateEducationExpVo
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<ResumeEducationExpListVo> updateResumeEducationExpInfo(Integer educationExpId, UpdateResumeEducationExpVo updateEducationExpVo, User user) {
        boolean flag = false;
        Integer result = null;
        ResumeEducationExpListVo resumeEducationExpListVo= null;
        ResumeInfoVo resumeInfoVo = (ResumeInfoVo) redisTemplate.opsForValue().get(Const.RESUME_INFO + user.getId());
        List<ResumeEducationExpListVo> educationExpListVos = resumeInfoVo.getEducationExpListVos();

        // 教育经历所属简历Id
        Integer resumeId = resumeMapper.selectResumeIdByUserId(user.getId());
        updateEducationExpVo.setResuid(resumeId);

        if (educationExpId != null) {
            // 更新教育经历
            // 设置教育经历id
            updateEducationExpVo.setId(educationExpId);
            // 更新数据库
            result = resumeMapper.updateResumeEducationExpById(updateEducationExpVo);
            if (result > 0) {
                flag = true;
                // 更新缓存
                // 遍历项目经历，删除对应id的工作经历
                Iterator<ResumeEducationExpListVo> iterator = educationExpListVos.iterator();
                while (iterator.hasNext()) {
                    ResumeEducationExpListVo value = iterator.next();
                    if (value.getId().equals(educationExpId)) {
                        iterator.remove();
                    }
                }
            }
        } else {
            // 新增项目经历
            result = resumeMapper.insertResumeEducationExp(updateEducationExpVo);
            if (result != null) {
                flag = true;
            }
        }

        if (flag) {
            // 将工作经历转换格式，添加进缓存
            resumeEducationExpListVo = this.assembleResumeEducationExpInfo(updateEducationExpVo);
            educationExpListVos.add(resumeEducationExpListVo);
            resumeInfoVo.setEducationExpListVos(educationExpListVos);
            redisTemplate.opsForValue().set(Const.RESUME_INFO + user.getId(), resumeInfoVo, 60 * 30, TimeUnit.SECONDS);
            return ServerResponse.createResponseBySuccess("更新教育经历成功！", resumeEducationExpListVo);
        }
        return ServerResponse.createResponseByErrorMsg("更新教育经历失败，请联系管理员！");
    }

    /**
     * 生成教育经历信息
     * @param updateEducationExpVo
     * @return
     */
    private ResumeEducationExpListVo assembleResumeEducationExpInfo(UpdateResumeEducationExpVo updateEducationExpVo) {
        ResumeEducationExpListVo resumeEducationExpListVo = new ResumeEducationExpListVo();
        resumeEducationExpListVo.setId(updateEducationExpVo.getId());
        // 获取学校的名称
        Integer schoolId = updateEducationExpVo.getSchoolId();
        resumeEducationExpListVo.setSchoolId(schoolId);
        resumeEducationExpListVo.setSchoolName(schoolMapper.selectByPrimaryKey(schoolId).getName());
        resumeEducationExpListVo.setSchoolType(updateEducationExpVo.getSchoolType());
        resumeEducationExpListVo.setDegree(updateEducationExpVo.getDegree());
        resumeEducationExpListVo.setMajor(updateEducationExpVo.getMajor());
        resumeEducationExpListVo.setStartdate(updateEducationExpVo.getStartdate());
        resumeEducationExpListVo.setEnddate(updateEducationExpVo.getEnddate());
        resumeEducationExpListVo.setSchoolexp(updateEducationExpVo.getSchoolexp());
        return resumeEducationExpListVo;
    }


    /**
     * 更新资格证书信息
     * @param certificationIdList
     * @param user
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<List<ResumeCertificationListVo>> updateResumeCertificationInfo(List<Integer> certificationIdList, User user) {
        Integer resumeId = resumeMapper.selectResumeIdByUserId(user.getId());
        // 删除该简历的所有证书
        Integer result = resumeCertificateMapper.deleteByResumeId(resumeId);
        if (result >= 0) {
            List<ResumeCertificationListVo> list = Lists.newArrayList();
            if (certificationIdList.size() > 0) {
                // 插入新的证书Id
                result = resumeCertificateMapper.batchInsertByRidCid(resumeId, certificationIdList);
                if (result > 0) {
                    // 遍历新证书，生成输出的结果
                    for (Integer cid : certificationIdList) {
                        ResumeCertificationListVo certificationListVo = new ResumeCertificationListVo();
                        String cname = credentialMapper.selectByPrimaryKey(cid).getCname();
                        certificationListVo.setId(cid);
                        certificationListVo.setCertificationName(cname);
                        list.add(certificationListVo);
                    }
                }
            }
            // 更新缓存
            ResumeInfoVo resumeInfoVo = (ResumeInfoVo) redisTemplate.opsForValue().get(Const.RESUME_INFO + user.getId());
            resumeInfoVo.setCertificationListVos(list);
            redisTemplate.opsForValue().set(Const.RESUME_INFO + user.getId(), resumeInfoVo, 60 * 60, TimeUnit.SECONDS);
            return ServerResponse.createResponseBySuccess("更新资格证书成功！", list);
        }
        return ServerResponse.createResponseByErrorMsg("更新资格证书失败！");
    }

    /**
     * 删除附件
     * @param type
     * @param user
     * @return
     */
    @Override
    @Transactional
    public ServerResponse deleteAttachment(String type, User user) {
        String deletedUri = resumeMapper.selectUriByUserId(type, user.getId());
        Integer result = resumeMapper.updateResumeAttachmentByUserId(user.getId(), type);
        if (result > 0) {
            ResumeInfoVo resumeInfoVo = (ResumeInfoVo) redisTemplate.opsForValue().get(Const.RESUME_INFO + user.getId());
            if (resumeInfoVo != null) {
                ResumeBaseInfoVo baseInfoVo = resumeInfoVo.getResumeBaseInfoVo();
                if (StringUtils.equals(type, "resume")) {
                    baseInfoVo.setResumeattachment(null);
                } else if (StringUtils.equals(type, "other")) {
                    baseInfoVo.setOtherattachment(null);
                }
                resumeInfoVo.setResumeBaseInfoVo(baseInfoVo);
                redisTemplate.opsForValue().set(Const.RESUME_INFO + user.getId(), resumeInfoVo, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
            }
            return ServerResponse.createResponseBySuccess("删除成功！", deletedUri);
        }
        return ServerResponse.createResponseByErrorMsg("删除失败！");
    }
}
