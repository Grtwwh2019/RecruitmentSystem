package com.zzj.recruitment.service.Backend.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.dao.*;
import com.zzj.recruitment.pojo.*;
import com.zzj.recruitment.service.Backend.IAdminManageService;
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
 * since 2020-03-22 15:31:41
 */
@Service("adminManageService")
@Slf4j
public class AdminManageServiceImpl implements IAdminManageService {

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    IndustrysetMapper industrysetMapper;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    AnnounceMapper announceMapper;

    @Autowired
    EmploymentMapper employmentMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 删除公司信息
     *
     * @param companyId
     * @return
     */
    @Override
    public ServerResponse<String> deleteCompanyInfo(Integer companyId) {
        int result = companyMapper.deleteByPrimaryKey(companyId);
        // todo 删除公司信息会涉及到其他相关的信息吗？需要删除该公司下的所有员工吗？
        if (result > 0) {
            // 缓存也要删除
            if (redisTemplate.hasKey(Const.COMPANY_DETAIL + companyId)) {
                Boolean delete = redisTemplate.delete(Const.COMPANY_DETAIL + companyId);
                log.info("缓存招聘信息删除结果：" + delete);
            }
            return ServerResponse.createResponseBySuccessMsg("删除公司信息成功！");
        }
        return ServerResponse.createResponseByErrorMsg("删除公司信息失败！");
    }

    /**
     * 修改公司信息
     * @param updateCompany
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<CompanyDetailVo> updateCompanyInfo(Company updateCompany) {
        // 更新数据库
        int result = companyMapper.updateByPrimaryKey(updateCompany);
        if (result > 0) {
            CompanyDetailVo companyDetail = this.assembleCompanyDetail(updateCompany);
            // 更新缓存
            redisTemplate.opsForValue().set(Const.COMPANY_DETAIL + updateCompany.getId(), companyDetail, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
            return ServerResponse.createResponseBySuccess("更新公司信息成功！", companyDetail);
        }
        return ServerResponse.createResponseByErrorMsg("更新公司信息失败！");
    }

    /**
     * 生成CompanyDetail返回
     * @param updateCompany
     * @return
     */
    private CompanyDetailVo assembleCompanyDetail(Company updateCompany) {
        CompanyDetailVo companyDetail = new CompanyDetailVo();
        companyDetail.setId(updateCompany.getId());
        companyDetail.setLogo(updateCompany.getLogo());
        companyDetail.setCname(updateCompany.getCname());
        companyDetail.setCintroduction(updateCompany.getCintroduction());
        companyDetail.setFinancing(updateCompany.getFinancing());
        companyDetail.setIndustryid(updateCompany.getIndustryid());
        // 获得行业
        Industryset industry = industrysetMapper.selectByPrimaryKey(updateCompany.getIndustryid());
        companyDetail.setIndustry(industry.getIname());
        companyDetail.setCsize(updateCompany.getCsize());
        //获得城市
        companyDetail.setCcityId(updateCompany.getCcity());
        City city = cityMapper.selectByPrimaryKey(updateCompany.getCcity());
        companyDetail.setCcityName(city.getCityname());
        companyDetail.setFullname(updateCompany.getFullname());
        companyDetail.setRepresentative(updateCompany.getRepresentative());
        companyDetail.setAssets(updateCompany.getAssets());
        companyDetail.setEstablishtime(updateCompany.getEstablishtime());
        companyDetail.setPhoto(updateCompany.getPhoto());
        return companyDetail;
    }


    /**
     * 获取注册企业账号的用户列表
     * @return
     * @param auth：动态查找已认证和未认证的企业账号（1---已认证，2---未认证）
     * @param pageNum
     */
    @Override
    public ServerResponse<PageInfo> getApplyEntAccountList(Integer pageNum, Integer auth) {
        PageHelper.startPage(pageNum, 15);
        // authentication：是否已认证，1---通过，2---否，3---拒绝 4---企业用户待认证 ,5---企业账号待认证
        int status = auth == 1 ? Const.authentication.PASS.getCode() : Const.authentication.TO_BE_ENTACCOUNT.getCode();
        List<EnterpriseAccountListVo> enterpriseAccountList = userMapper.selectEnterpriseAccount(status);
        PageInfo pageInfo = new PageInfo(enterpriseAccountList);
        if (pageInfo != null) {
            return ServerResponse.createResponseBySuccess("获取注册企业账号的用户列表成功！", pageInfo);
        }
        return ServerResponse.createResponseByErrorMsg("获取注册企业账号的用户列表失败！");
    }


    /**
     * 查看企业账号认证信息
     *
     * @param userid 企业账号Id
     * @return
     */
    @Override
    public ServerResponse<User> getEntAccountAuthInfo(Integer userid) {
        User entAccount = userMapper.selectByPrimaryKey(userid);
        if (entAccount != null) {
            entAccount.setPassword(null);
            return ServerResponse.createResponseBySuccess("获取企业账号认证信息成功！", entAccount);
        }
        return ServerResponse.createResponseByErrorMsg("获取企业账号认证信息失败！");
    }

    /**
     * 审批企业账号
     * @param entUserId
     * @param approve  1---通过认证； 2---拒绝认证
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<String> approveEnterpriseAccount(Integer entUserId, Integer approve) {
        // 需要修改该用户的角色
        if (approve == 1) {
            // 通过认证
            Integer result = userMapper.updateAuthenticationByUserId(entUserId, Const.authentication.PASS.getCode());
            if (result > 0) {
                // 修改角色
                result = userRoleMapper.insertNewRole(entUserId, Const.Role.ROLE_enterAccount.getCode());
                if (result > 0) {
                    return ServerResponse.createResponseBySuccessMsg("该用户：" + entUserId + "，通过认证！");
                }
            }
        } else if (approve == 2) {
            // 拒绝认证
            Integer result = userMapper.updateAuthenticationByUserId(entUserId, Const.authentication.DENY.getCode());
            // 删除角色
            if (result > 0) {
                userRoleMapper.deleteByUidRid(entUserId, Const.Role.ROLE_enterAccount.getCode());
            }
            return ServerResponse.createResponseBySuccessMsg("该用户：" + entUserId + "，拒绝认证！");
        }
        return ServerResponse.createResponseByErrorMsg("审批失败，请联系管理员！");
    }


    /**
     * 获取所有用户
     *
     * @param userSearchVo ：动态获取
     * @param pageNum
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getAllUserList(UserSearchVo userSearchVo, Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<UserDetailInfoVo> userList = userMapper.selectUserListBySearchVo(userSearchVo);
        PageInfo pageInfo = new PageInfo(userList);
        if (pageInfo != null) {
            return ServerResponse.createResponseBySuccess("获取用户列表成功！", pageInfo);
        }
        return ServerResponse.createResponseByErrorMsg("获取用户列表失败！");
    }


    /**
     * 启用、禁用账号
     * @param userId 管理的用户Id
     * @param status 1---启用，2---禁用。
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<String> mangeAccount(Integer userId, Integer status) {
        // 设置authentication：是否已认证，0---禁用（账号被禁用），1---是，2---否(账号正常)，3---拒绝 4---企业用户待认证 ,5---企业账号待认证（求职者注册默认为2，企业账号注册默认为5）
        if (status == 1) {
            // 启用账号，authentication恢复其认证情况
            Integer userAuth = (Integer) redisTemplate.opsForValue().get(Const.FORBID_USER_ORG_AUTH + userId);
            if (userAuth == null) {
                return ServerResponse.createResponseBySuccessMsg("该用户状态正常，已启用，用户Id为：" + userId);
            }
            Integer result = userMapper.updateAuthenticationByUserId(userId, userAuth);
            if (result > 0) {
                // 删除缓存中的认证情况
                redisTemplate.delete(Const.FORBID_USER_ORG_AUTH + userId);
                return ServerResponse.createResponseBySuccessMsg("启用账号成功，启用用户Id为：" + userId);
            }
        } else if (status == 2) {
            // 禁用账号，在缓存中保存其原来的认证情况，然后将authentication设为0表示账号被禁用
            User user = userMapper.selectByPrimaryKey(userId);
            redisTemplate.opsForValue().set(Const.FORBID_USER_ORG_AUTH + userId, user.getAuthentication());
            Integer result = userMapper.updateAuthenticationByUserId(userId, Const.authentication.FORBIDED.getCode());
            if (result > 0) {
                return ServerResponse.createResponseBySuccessMsg("禁用账号成功，被禁用户Id为：" + userId);
            }
        }
        return ServerResponse.createResponseByErrorMsg("账号管理操作失败，请联系管理员！");
    }

    /**
     * 获取公告列表
     * @param announceSearchVo：动态搜索
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getAllAnnounceList(AnnounceSearchVo announceSearchVo, Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<AnnounceListVo> announceList =  announceMapper.selectAnnounceListBySearchVo(announceSearchVo);
        PageInfo pageInfo = new PageInfo(announceList);
        if (pageInfo != null) {
            return ServerResponse.createResponseBySuccess("获取公告列表成功！", pageInfo);
        }
        return ServerResponse.createResponseByErrorMsg("获取公告列表失败！");
    }

    /**
     * 发布公告/更新
     *
     * @param announce
     * @param announceId
     * @param user
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<AnnounceListVo> addOrUpdateAnnounce(Announce announce, Integer announceId, User user) {
        boolean flag = false;
        announce.setSender(user.getId());
        if (announceId != null) {
            // 存在announceId，即更新公告
            announce.setId(announceId);
            int result = announceMapper.updateByPrimaryKeySelective(announce);
            if (result > 0) {
                flag = true;
            }
        } else if (announceId == null) {
            // 不存在 announceId， 即发布公告
            announce.setStatus(1);
            int result = announceMapper.insertSelective(announce);
            if (result > 0) {
                flag = true;
            }
        }
        if (flag) {
            // 发布以后存入缓存????
            AnnounceListVo announceListVo = this.assembleAnnounceDetail(announce);
//            redisTemplate.opsForValue().set(Const.ANNOUNCE_DETAIL + announce.getId(), announceListVo, Const.RedisCacheExtime.REDIS_EXTIME_DAY, TimeUnit.SECONDS);
            return ServerResponse.createResponseBySuccess((announceId == null ? "发布" : "更新" ) + "公告成功！", announceListVo);
        }
        return ServerResponse.createResponseByErrorMsg("发布公告失败！");
    }

    /**
     * 生成公告详细信息
     * @param announce
     */
    private AnnounceListVo assembleAnnounceDetail(Announce announce) {
        AnnounceListVo announceListVo = new AnnounceListVo();
        announceListVo.setId(announce.getId());
        announceListVo.setTitle(announce.getTitle());
        announceListVo.setContent(announce.getContent());
        announceListVo.setType(announce.getType());
        User sender = userMapper.selectByPrimaryKey(announce.getSender());
        announceListVo.setSender(sender.getNickname());
        announceListVo.setReceiver(announce.getReceiver());
        announceListVo.setCreateTime(announce.getCreatetime());
//        announceListVo.setStatus(announce.getStatus());
        return announceListVo;
    }

    /**
     * 删除公告
     *
     * @param announceId
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<String> deleteAnnounce(Integer announceId) {
        int result = 0;
        // 删除数据库
        if (announceId != null && announceId > 0) {
            result = announceMapper.deleteByPrimaryKey(announceId);
        }
        Boolean delete = false;
        // 删除缓存
        if (result > 0) {
            delete = redisTemplate.hasKey(Const.ANNOUNCE_DETAIL + announceId) ? redisTemplate.delete(Const.ANNOUNCE_DETAIL + announceId) : true;
        }
        if (delete) {
            return ServerResponse.createResponseBySuccessMsg("删除公告成功！");
        }
        return ServerResponse.createResponseByErrorMsg("删除公告失败！");
    }

    /**
     * 获取所有招聘信息列表
     *
     * @param employmentSearchVo：动态查找
     * @param pageNum
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getPositionList(CompanyPositionSearchVo employmentSearchVo, Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<PosisitonListVo> employmentList = employmentMapper.selectPositionBySearchVoUserId(employmentSearchVo, employmentSearchVo.getPublisherId());
        PageInfo pageResult = new PageInfo(employmentList);
        return ServerResponse.createResponseBySuccess("获取招聘信息列表成功！", pageResult);
    }

    /**
     * 封禁/解封招聘信息
     *
     * @param positionId
     * @param status：    0---封禁，1---正常(解封)
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<String> forbiddenPosition(Integer positionId, Integer status) {
        // 从缓存获取
        PositionDetailVo positionDetail = (PositionDetailVo) redisTemplate.opsForValue().get(Const.POSITION_DETAIL + positionId);
        if (positionDetail == null) {
            positionDetail = employmentMapper.selectStatusById(positionId);
        }
        boolean flag = positionDetail.getEstatus() != status; // true： 状态已改变
        if (flag) {
            Integer result = employmentMapper.updateStatusById(positionId, status);
            if (status == Const.employmentStatus.FORBIDDEN.getStatus()) {
                // 封禁
                // 删除在缓存中的招聘信息
//                if (result > 0) {
//                    redisTemplate.delete(Const.POSITION_DETAIL + positionId);
//                }
            } else if (status == Const.employmentStatus.NORMAL.getStatus()) {
                // 解封
            }
            if (result > 0) {
                redisTemplate.delete(Const.POSITION_DETAIL + positionId);
                return ServerResponse.createResponseBySuccessMsg((status == 0 ? "封禁" : "解封" )+ "成功！");
            }
        } else {
            return ServerResponse.createResponseBySuccessMsg("状态未改变，已" + (status == 0 ? "封禁" : "解封"));
        }
        return ServerResponse.createResponseByErrorMsg("操作失败，请联系管理员");
    }

    @Override
    public ServerResponse getUserListByUserName(String userName, User user) {
        List<UserListVo> userList = userMapper.selectUserListByUserName(userName, user.getId());
        return ServerResponse.createResponseBySuccess(userList);
    }
}
