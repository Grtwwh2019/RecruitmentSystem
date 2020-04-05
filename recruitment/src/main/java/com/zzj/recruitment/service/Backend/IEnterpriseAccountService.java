package com.zzj.recruitment.service.Backend;

import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.vo.EnterpriseUserInfoVo;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 16:45:15
 */
public interface IEnterpriseAccountService {
    ServerResponse<EnterpriseUserInfoVo> getEnterpriseUserAuthenticationInfo(Integer entUserId, User user);

    ServerResponse approveEnterpriseUser(Integer entUserId, Integer approve);

    ServerResponse getApplyEntUserList(Integer pageNum, User user);
}
