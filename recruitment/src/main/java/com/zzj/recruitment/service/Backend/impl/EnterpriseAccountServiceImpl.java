package com.zzj.recruitment.service.Backend.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.dao.UserMapper;
import com.zzj.recruitment.dao.UserRoleMapper;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.service.Backend.IEnterpriseAccountService;
import com.zzj.recruitment.util.PropertiesUtil;
import com.zzj.recruitment.vo.EnterpriseUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 16:45:24
 */
@Service("enterpriseAccountService")
public class EnterpriseAccountServiceImpl implements IEnterpriseAccountService {


    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    PropertiesUtil propertiesUtil;

    /**
     * 获取申请企业用户的用户列表
     * 根据该企业账户获得该申请认证该企业的企业用户
     *
     * @param pageNum
     * @return
     */
    @Override
    public ServerResponse getApplyEntUserList(Integer pageNum, User user) {
        PageHelper.startPage(pageNum, 10);
        List<EnterpriseUserInfoVo> enterpriseUserInfoList = userMapper.selectEnterpriseUserList(user.getCompanyid(), user.getId());
        PageInfo pageInfo = new PageInfo(enterpriseUserInfoList);
        if (pageInfo != null) {
            return ServerResponse.createResponseBySuccess("获取申请企业用户的用户列表成功！", pageInfo);
        }
        return ServerResponse.createResponseByErrorMsg("获取申请企业用户的用户列表失败！");
    }

    /**
     * 查看企业用户认证信息
     *
     * @param entUserId ： 用户的Id
     * @param user
     * @return
     */
    @Override
    public ServerResponse<EnterpriseUserInfoVo> getEnterpriseUserAuthenticationInfo(Integer entUserId, User user) {
        // 得到申请用户的信息，确保该用户是该企业账号所属的用户
        EnterpriseUserInfoVo entUser = userMapper.selectEnterpriseUserById(entUserId, user.getCompanyid());
        if (entUser != null) {
            String url = propertiesUtil.getFtp_server_http_prefix() + entUser.getCardPhoto();
            entUser.setCardPhoto(url);
            return ServerResponse.createResponseBySuccess("获取企业用户认证信息成功！", entUser);
        }
        return ServerResponse.createResponseByErrorMsg("获取企业用户认证信息失败！");
    }

    /**
     * 审批企业用户
     * v1：更新：为通过认证的用户添加角色
     * @param entUserId ： 用户的Id
     * @param approve ： 1---通过认证； 2---拒绝认证
     * @return
     */
    @Override
    @Transactional
    public ServerResponse approveEnterpriseUser(Integer entUserId, Integer approve) {
        // 更新user的authentication：是否已认证企业用户，1---是，2---否，3---拒绝 4---待认证（默认为2）
        Integer result = 0;
        String resultstr = "";
        if (approve == 1) {
            // 通过认证，要修改其角色
            result = userMapper.updateAuthenticationByUserId(entUserId, Const.authentication.PASS.getCode());
            if (result > 0) {
                result = userRoleMapper.insertNewRole(entUserId, Const.Role.ROLE_enterUser.getCode());
            }
            resultstr = "用户ID："+ entUserId + "，已通过认证！";
        } else if (approve == 2) {
            // 拒绝认证时，如果是已经存在角色，则要删除对应角色
            result = userMapper.updateAuthenticationByUserId(entUserId, Const.authentication.DENY.getCode());
            if (result > 0) {
                userRoleMapper.deleteByUidRid(entUserId, Const.Role.ROLE_enterUser.getCode());
            }
            resultstr = "用户："+ entUserId + "，认证已拒绝！";
        }
        if (result > 0) {
            return ServerResponse.createResponseBySuccess(resultstr);
        }
        return ServerResponse.createResponseByErrorMsg("审批失败，未知错误，请联系管理员！");
    }
}
