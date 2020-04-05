package com.zzj.recruitment.controller.Backend;

import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.pojo.Role;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.service.Backend.IEnterpriseAccountService;
import com.zzj.recruitment.service.Backend.impl.EnterpriseAccountServiceImpl;
import com.zzj.recruitment.service.IRoleService;
import com.zzj.recruitment.util.CookieUtil;
import com.zzj.recruitment.vo.EnterpriseUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 企业账号 Controller
 *
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 16:44:04
 */
@RestController
@RequestMapping("/entAccount")
public class EnterpriseAccountController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    IEnterpriseAccountService enterpriseAccountService;

    @Autowired
    IRoleService roleService;


    /**
     * 获取申请企业用户的用户列表
     * 根据该企业账户获得该申请认证该企业的企业用户
     * @param pageNum
     * @param request
     * @return
     */
    @GetMapping("/getApplyEntUserList.do/{pageNum}")
    public ServerResponse getApplyEntUserList(@PathVariable("pageNum") Integer pageNum, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为企业账号 (roleId == 3)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_enterAccount.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    return enterpriseAccountService.getApplyEntUserList(pageNum, user);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }



    /**
     * 查看企业用户认证详细信息
     * @param entUserId ： 用户的Id
     * @param request
     * @return
     */
    @GetMapping("/getEntUserAuthInfo.do/{entUserId}")
    public ServerResponse<EnterpriseUserInfoVo> getEnterpriseUserAuthenticationInfo(@PathVariable("entUserId") Integer entUserId, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为企业账号 (roleId == 3)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_enterAccount.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    return enterpriseAccountService.getEnterpriseUserAuthenticationInfo(entUserId, user);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }

    /**
     * 审批企业用户
     * @param entUserId ： 用户的Id
     * @param approve ： 1---通过认证； 2---拒绝认证
     * @param request
     * @return
     */
    @PostMapping("/approveEntUser.do/{entUserId}/{approve}")
    public ServerResponse approveEnterpriseUser(@PathVariable("entUserId") Integer entUserId, @PathVariable("approve") Integer approve, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为企业账号 (roleId == 3)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_enterAccount.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    return enterpriseAccountService.approveEnterpriseUser(entUserId, approve);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }

}
