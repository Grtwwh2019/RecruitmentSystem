package com.zzj.recruitment.service.Backend.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.dao.*;
import com.zzj.recruitment.pojo.City;
import com.zzj.recruitment.pojo.PositionTypeSet;
import com.zzj.recruitment.pojo.Resume;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.service.Backend.IEnterpriseUserService;
import com.zzj.recruitment.service.Backend.IResumeService;
import com.zzj.recruitment.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 18:45:52
 */
@Service("enterpriseUserService")
@Slf4j
public class EnterpriseUserServiceImpl implements IEnterpriseUserService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    EmploymentMapper employmentMapper;

    @Autowired
    PositionTypeSetMapper positionTypeSetMapper;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    ResumeMapper resumeMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    IResumeService resumeService;


    /**
     * 根据登录的企业用户，获取企业用户发布的招聘信息列表
     *
     * @param pageNum
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getEmploymentListByUserId(Integer pageNum, CompanyPositionSearchVo employmentSearchVo, User user) {
        PageHelper.startPage(pageNum, 10);
        // todo 暂时没有其他查找条件，所以这里设为null
        employmentSearchVo.setCity(null);
        employmentSearchVo.setCname(null);
        employmentSearchVo.setCity(null);
        employmentSearchVo.setCsize(null);
        employmentSearchVo.setEducation(null);
        employmentSearchVo.setFinacing(null);
        List<PosisitonListVo> employmentList = employmentMapper.selectPositionBySearchVoUserId(employmentSearchVo, user.getId());
        PageInfo pageResult = new PageInfo(employmentList);
        return ServerResponse.createResponseBySuccess("获取招聘信息列表成功！", pageResult);
    }

    /**
     * 发布招聘信息
     *
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<PositionDetailVo> addOrUpdateEmployment(AddUpdateEmploymentVo addUpdateEmploymentVo, Integer eid, User user) {
        boolean isSuccess = false;
        addUpdateEmploymentVo.setEstatus(1);
        addUpdateEmploymentVo.setEpublisher(user.getId());
        addUpdateEmploymentVo.setEcompanyid(user.getCompanyid());
        // 判断是删除还是新增
        if (eid == null) {
            // 新增
            // 1.数据库新增
            Integer result = employmentMapper.insertByEmploymentVo(addUpdateEmploymentVo);
            if (result > 0) {
                // 2.缓存新增
                isSuccess = true;
            }
        } else if (eid != null) {
            // 修改
            // 1.数据库修改
            addUpdateEmploymentVo.setId(eid);
            addUpdateEmploymentVo.setEupdater(user.getId());
            Integer result = employmentMapper.updateByEmploymentVo(addUpdateEmploymentVo);
            if (result > 0) {
                // 2.缓存修改
                isSuccess = true;
            }
        }
        if (isSuccess) {
            PositionDetailVo employmentDetail = this.assemblePositionDetail(addUpdateEmploymentVo, user);
            // 缓存修改
            redisTemplate.opsForValue().set(Const.POSITION_DETAIL + addUpdateEmploymentVo.getId(), employmentDetail, Const.RedisCacheExtime.REDIS_EXTIME_DAY, TimeUnit.SECONDS);
            return ServerResponse.createResponseBySuccess((eid == null ? "添加招聘信息成功！" : "修改招聘信息成功！"), employmentDetail);
        }
        return ServerResponse.createResponseByErrorMsg((eid == null ? "添加招聘信息失败！" : "修改招聘信息失败！"));
    }

    /**
     * 生成返回的职位详细信息
     * @param addUpdateEmploymentVo
     * @return
     */
    private PositionDetailVo assemblePositionDetail(AddUpdateEmploymentVo addUpdateEmploymentVo, User user) {
        PositionDetailVo employmentDetail = new PositionDetailVo();
        employmentDetail.setId(addUpdateEmploymentVo.getId());
        employmentDetail.setEname(addUpdateEmploymentVo.getEname());
        PositionTypeSet etype = positionTypeSetMapper.selectByPrimaryKey(addUpdateEmploymentVo.getEtypeid());
        employmentDetail.setEtype(etype.getPtname());
        City city = cityMapper.selectByPrimaryKey(addUpdateEmploymentVo.getEcityid());
        employmentDetail.setEcity(city.getCityname());
        employmentDetail.setEsalaryfloor(addUpdateEmploymentVo.getEsalaryfloor());
        employmentDetail.setEsalaryceilie(addUpdateEmploymentVo.getEsalaryceilie());
        Integer ecompanyid = addUpdateEmploymentVo.getEcompanyid();
        employmentDetail.setEcompanyId(ecompanyid);
        CompanyDetailVo companyDetailVo = companyMapper.selectDetailByCompanyId(ecompanyid);
        employmentDetail.setEcompany(companyDetailVo.getCname());
        employmentDetail.setEresponsibility(addUpdateEmploymentVo.getEresponsibility());
        employmentDetail.setErequirement(addUpdateEmploymentVo.getErequirement());
        employmentDetail.setPublisher(user.getNickname());
        employmentDetail.setCreatetime(addUpdateEmploymentVo.getCreatetime());
        employmentDetail.setEstatus(addUpdateEmploymentVo.getEstatus());
        return employmentDetail;
    }

    /**
     * 删除招聘信息
     *
     * @param eid
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<String> deleteEmploymentInfo(Integer eid) {
        int result = employmentMapper.deleteByPrimaryKey(eid);
        if (result > 0) {
            // 缓存也要删除
            if (redisTemplate.hasKey(Const.POSITION_DETAIL + eid)) {
                Boolean delete = redisTemplate.delete(Const.POSITION_DETAIL + eid);
                log.info("缓存招聘信息删除结果：" + delete);
            }
            return ServerResponse.createResponseBySuccessMsg("删除招聘信息成功！");
        }
        return ServerResponse.createResponseByErrorMsg("删除招聘信息失败！");
    }

    /**
     * 获取求职者简历列表
     * 根据登录的userId获取该id发布的职位信息
     * @param pageNum
     * @param resumeListSearchVo
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getUserResumeList(Integer pageNum, ResumeListSearchVo resumeListSearchVo, User user) {
        PageHelper.startPage(pageNum, 15);
        List<ResumeBaseInfoVo> baseInfoList = resumeMapper.selectDeliveryResumeByUserId(resumeListSearchVo, user.getId());
        if (baseInfoList != null) {
            PageInfo pageInfo = new PageInfo(baseInfoList);
            return ServerResponse.createResponseBySuccess("获取求职者简历列表成功！", pageInfo);
        }
        return ServerResponse.createResponseByErrorMsg("获取求职者简历列表失败！");
    }

    /**
     * 查看投递人的简历的详情信息
     *
     * @param "resumeid": 2033, 根据简历Id找到用户id，根据用户id找到用户信息，根据用户信息，生成详细简历
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<ResumeInfoVo> getResumeDetailInfo(Integer resumeId, User user) {
        // 需要判断该简历Id是否属于该user，也就是说该简历Id投递的职位是否为该用户发布的
        Integer publisher = employmentMapper.selectPublisherByResumeId(resumeId);
        // 如果属于，才能获取
        if (publisher != null && publisher.equals(user.getId())) {
            Resume employerResume = resumeMapper.selectByPrimaryKey(resumeId);
            User employer = userMapper.selectByPrimaryKey(employerResume.getRuserid());
            ServerResponse<ResumeInfoVo> response = resumeService.returnResumeInfo(employer);
            if (response.isSuccess()) {
                return response;
            }
        }
        return ServerResponse.createResponseByErrorMsg("获取简历详情失败！");
    }
}
