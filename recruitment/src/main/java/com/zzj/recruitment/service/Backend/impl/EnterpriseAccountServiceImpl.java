package com.zzj.recruitment.service.Backend.impl;

import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.dao.UserMapper;
import com.zzj.recruitment.service.Backend.IEnterpriseAccountService;
import com.zzj.recruitment.util.PropertiesUtil;
import com.zzj.recruitment.vo.EnterpriseUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    PropertiesUtil propertiesUtil;

    /**
     * 查看企业用户认证信息
     *
     * @param entUserId ： 用户的Id
     * @return
     */
    @Override
    public ServerResponse<EnterpriseUserInfoVo> getEnterpriseUserAuthenticationInfo(Integer entUserId) {
        // 得到申请用户的信息
        EnterpriseUserInfoVo entUser = userMapper.selectEnterpriseUserById(entUserId);
        if (entUser != null) {
            String url = propertiesUtil.getFtp_server_http_prefix() + entUser.getCardPhoto();
            entUser.setCardPhoto(url);
            return ServerResponse.createResponseBySuccess("获取企业用户认证信息成功！", entUser);
        }
        return ServerResponse.createResponseByErrorMsg("获取企业用户认证信息失败！");
    }

    /**
     * 审批企业用户
     *
     * @param entUserId ： 用户的Id
     * @param approve ： 1---通过认证； 2---拒绝认证
     * @return
     */
    @Override
    public ServerResponse approveEnterpriseUser(Integer entUserId, Integer approve) {
        // 更新user的authentication：是否已认证企业用户，1---是，2---否，3---拒绝 4---待认证（默认为2）
        Integer result = 0;
        String resultstr = "";
        if (approve == 1) {
            result = userMapper.updateAuthenticationByUserId(entUserId, 1);
            resultstr = "该用户已通过认证！";
        } else if (approve == 2) {
            result = userMapper.updateAuthenticationByUserId(entUserId, 3);
            resultstr = "该用户认证已拒绝！";
        }
        if (result > 0) {
            return ServerResponse.createResponseBySuccess(resultstr);
        }
        return ServerResponse.createResponseByErrorMsg("审批失败，未知错误，请联系管理员！");
    }
}
