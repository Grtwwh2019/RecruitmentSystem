package com.zzj.recruitment.service;

import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.pojo.User;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-11 17:45:33
 */
public interface IUserService {

    ServerResponse<User> userlogin(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String value, String type);

    ServerResponse<User> updateUserInfo(User user, User currentUser);

    ServerResponse<String> selectVerifyType(String username, String type);

    ServerResponse<String> checkCode(String username, String verificationCode, String type);

    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String username, String passwordOld, String passwordNew);
}
