package com.zzj.recruitment.controller.Portal;

import com.zzj.recruitment.bo.CaptchaEntity;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.pojo.Role;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.service.ICaptchaService;
import com.zzj.recruitment.service.IRoleService;
import com.zzj.recruitment.service.IUserService;
import com.zzj.recruitment.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-11 14:17:37
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    IUserService userService;

    @Autowired
    ICaptchaService captchaService;

    @Autowired
    IRoleService roleService;


//    @GetMapping("/login.do")
//    public ServerResponse<String> login() {
//        return ServerResponse.createResponseByErrorMsg("您没有权限，请先登录！");
//    }

    /**
     * 登录接口
     *
     * @param username
     * @param password
     * @param captchaToken：登录验证码的token
     * @param captcha：登录输入的验证码
     * @param session：记录携带自己Cookie的SessionID
     * @param httpServletResponse：需要Response来写回cookie 用Cookie来保存SessionID，那么不管请求发送到哪个服务器，
     *                                                可以通过浏览器发送请求携带的Cookie得到SessionID，进而获取到用户信息
     * @return
     */
    @PostMapping("/login.do")
    public ServerResponse<User> userLogin(String username, String password, String captchaToken,
                                          String captcha, HttpSession session,
                                          HttpServletResponse httpServletResponse) {
        // 判断输入的token是否存在（缓存中是否存在)
        String redisCaptcha = (String) redisTemplate.opsForValue().get(captchaToken);
        if (redisCaptcha == null) {
            // 如果不存在
            return ServerResponse.createResponseByErrorMsg("请先获取验证码！");
        }
        // 如果存在，直接获取，然后判断输入的验证码和存储的验证码是否相等
        if (StringUtils.equals(redisCaptcha, captcha)) {
            // 如果相等，进入登录
            ServerResponse<User> response = userService.userlogin(username, password);
            if (response.isSuccess()) {
                User user = response.getData();
                // 如果登录成功，将SessionID保存到cookie中
                // 将用户信息存储到redis中，key为SessionID，并且删掉验证码信息
                CookieUtil.writeLoginToken(httpServletResponse, session.getId());
                redisTemplate.opsForValue().set(session.getId(), user,
                        Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
            }
            // 如果密码错误，删掉原验证码信息，需要重新获取验证码
            captchaService.deleteToken(captchaToken);
            return response;
        }
        // 如果验证失败，重新获取验证码（先删除redis中的验证码，然后重新调用captcha接口
        if (captchaToken != null) {
            captchaService.deleteToken(captchaToken);
        }
        // 如果验证失败，前端接收到失败的信息，则重新调用captcha接口获取验证码
        return ServerResponse.createResponseByErrorMsg("验证失败，请重新输入验证码！");
    }

    /**
     * 注册用户接口
     * @param user
     * @param role: 角色 1---求职者，4---企业账号
     * @return
     */
    @PostMapping("/register.do/{role}")
    public ServerResponse<String> register(User user, @PathVariable Integer role, String captchaToken,
                                           String captcha) {
        // 判断输入的token是否存在（缓存中是否存在)
        String redisCaptcha = (String) redisTemplate.opsForValue().get(captchaToken);
        if (redisCaptcha == null) {
            // 如果不存在
            return ServerResponse.createResponseByErrorMsg("请先获取验证码！");
        }
        // 如果存在，直接获取，然后判断输入的验证码和存储的验证码是否相等
        if (StringUtils.equals(redisCaptcha, captcha)) {
            // 如果相等，进入注册
            user.setAuthentication(role == 1 ? 2 : 5);
            ServerResponse<String> response = userService.register(user, role);
            captchaService.deleteToken(captchaToken);
            return response;
        }
        if (captchaToken != null) {
            captchaService.deleteToken(captchaToken);
        }
        // 如果验证失败，前端接收到失败的信息，则重新调用captcha接口获取验证码
        return ServerResponse.createResponseByErrorMsg("验证失败，请重新输入验证码！");
    }

    /**
     * 校验接口：昵称、账号、邮箱、手机号、密码的校验
     * @param value
     * @param type
     * @return
     */
    @PostMapping("/checkValid.do")
    public ServerResponse<String> checkValid(String value, String type) {
        return userService.checkValid(value, type);
    }

    /**
     * 登录状态下获取用户信息
     *
     * @return
     */
    @GetMapping("/getUserInfo.do")
    public ServerResponse<User> getUserInfo(HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                return ServerResponse.createResponseBySuccess("获取成功", user);
            }
        }
        return ServerResponse.createResponseByErrorMsg("用户未登录！");
    }

    /**
     * 更新昵称
     */
    @PostMapping("/updateUserInfo.do")
    public ServerResponse<User> updateUserInfo(User user, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User currentUser = (User) redisTemplate.opsForValue().get(loginToken);
            // todo 后期优化为 要验证码当前手机号或邮箱 才能修改手机或邮箱
            if (currentUser != null) {
                ServerResponse<User> response = userService.updateUserInfo(user, currentUser);
                if (response.isSuccess()) {
                    redisTemplate.opsForValue().set(loginToken, response.getData(), Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
                }
                return response;
            }
        }
        return ServerResponse.createResponseByErrorMsg("用户未登录！");
    }

    /**
     * 未登录时忘记密码，选择修改密码的途径
     * @param username
     * @param type：type可以是email、telephone
     * @return
     */
    @GetMapping("/forgetGetPsd.do")
    public ServerResponse<String> forgetGetPsd(String username, String type) {
        // todo 后期改为接收验证码验证，即在这里发送验证码
        // 目前是返回手机号或邮箱
        ServerResponse<String> response = userService.selectVerifyType(username, type);
        return response;
    }

    /**
     *
     * @param username
     * @param verificationCode ：收到的验证码
     * @return
     */
    @PostMapping("/forgetCheckCode.do")
    public ServerResponse<String> forgetCheckCode(String username, @RequestParam("verifyCode") String verificationCode, String type) {
        return userService.checkCode(username, verificationCode, type);
    }


    /**
     * 重新设置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @PostMapping("/forgetResetPassword.do")
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return userService.forgetResetPassword(username, passwordNew, forgetToken);
    }


    /**
     * 登录状态下的重置密码
     *
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @PostMapping("/resetPassword.do")
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                return userService.resetPassword(user.getUsername(), passwordOld, passwordNew);
            }
        }
        return ServerResponse.createResponseByErrorMsg("您没有权限，请先登录！");
    }

    /**
     * @param httpServletRequest：用于获取现有Cookie
     * @param httpServletResponse：用于返回删除SessionID后的cookie
     * @return
     */
    @PostMapping("/logout.do")
    public ServerResponse<String> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        // 从Cookie中返回用户登录的SessionID
        // 删除Cookie中的SessionID
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        CookieUtil.delLoginToken(httpServletRequest, httpServletResponse);
        // 从缓存中删除SessionID
        User user = (User) redisTemplate.opsForValue().get(loginToken);
        if (user != null) {
            redisTemplate.delete(loginToken);
            redisTemplate.delete(Const.RESUME_INFO + user.getId());
            redisTemplate.delete(Const.collectionCache.POSITION + user.getId());
            redisTemplate.delete(Const.collectionCache.COMPANY + user.getId());
            redisTemplate.delete(Const.ANNOUNCE_LIST + user.getId());
            redisTemplate.delete(Const.RIGHT_RESOURCE + user.getId());
        }
        return ServerResponse.createResponseBySuccessMsg("注销成功！");
    }

    /**
     * 简历投递
     * @param employmentId
     * @param request
     * @return
     */
    @PostMapping("/deliver.do/{employmentId}")
    public ServerResponse<String> resumeDelivery(@PathVariable("employmentId") @Valid() @Min(value = 1,message = "参数出错，必须大于等于1！") @NotNull(message = "参数不能为空！") Integer employmentId, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                return userService.deliverResume(user, employmentId);
            }
        }
        return ServerResponse.createResponseByErrorMsg("您没有权限，请先登录！");
    }

    /**
     * 获取角色
     * @param request
     * @return
     */
    @GetMapping("/getHighestRole.do")
    public ServerResponse<Role> getHighestRole(HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                Role role = roleService.gethighestRole(user.getId());
                if (role.getId() > 0) {
                    return ServerResponse.createResponseBySuccess("获取角色成功！", role);
                }
            }
        }
        return ServerResponse.createResponseByErrorMsg("您没有权限，请先登录！");
    }

    /**
     * 获取用户收藏的状态
     * @param collectionId
     * @param request
     * @return
     */
    @GetMapping("/getUserCollectionStatus.do/{type}/{collectionId}")
    public ServerResponse getUserCollectionStatus(@PathVariable("type") Integer type, @PathVariable("collectionId") Integer collectionId, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                return userService.getUserCollectionStatus(type, collectionId, user);
            }
        }
        return ServerResponse.createResponseByErrorMsg("您没有权限，请先登录！");
    }








}
