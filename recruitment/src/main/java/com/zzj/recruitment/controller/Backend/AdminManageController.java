package com.zzj.recruitment.controller.Backend;

import com.github.pagehelper.PageInfo;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.common.constant.ResponseCode;
import com.zzj.recruitment.pojo.Announce;
import com.zzj.recruitment.pojo.Company;
import com.zzj.recruitment.pojo.Role;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.service.Backend.IAdminManageService;
import com.zzj.recruitment.service.IRoleService;
import com.zzj.recruitment.service.IUserService;
import com.zzj.recruitment.util.CookieUtil;
import com.zzj.recruitment.util.JsonUtil;
import com.zzj.recruitment.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 管理员
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-22 15:30:48
 */
@RestController
@RequestMapping("/admin")
public class AdminManageController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    IRoleService roleService;

    @Autowired
    IAdminManageService adminManageService;

    @Autowired
    IUserService userService;

    @PostMapping("/login.do")
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse){
        ServerResponse<User> response = userService.userlogin(username,password);
        if(response.isSuccess()){
            User user = response.getData();
            boolean isPermitted = false;
            List<Role> roles = roleService.getAllRolesByUserId(user.getId());
            for (Role role : roles) {
                if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                    isPermitted = true;
                    break;
                }
            }
            // 如果有权限
            if (isPermitted) {
                CookieUtil.writeLoginToken(httpServletResponse,session.getId());
                redisTemplate.opsForValue().set(session.getId(), user, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
                return response;
            } else{
                return ServerResponse.createResponseByErrorCustom(403,"不是管理员,无法登录");
            }
        }
        return response;
    }



    /**
     * 删除公司信息
     * @param companyId
     * @param request
     * @return
     */
    @PostMapping("/deleteCompany.do/{companyId}")
    public ServerResponse<String> deleteCompanyInfo(@PathVariable("companyId") Integer companyId, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为管理员 (roleId == 4)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    return adminManageService.deleteCompanyInfo(companyId);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }


    /**
     * 修改公司信息
     * @param company
     * @param request
     * @return
     */
    @PostMapping("/updateCompany.do")
    public ServerResponse<CompanyDetailVo> updateCompanyInfo(@Validated @RequestBody Company company, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为管理员 (roleId == 4)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    return adminManageService.updateCompanyInfo(company);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }


    /**
     * 获取注册企业账号的用户列表
     * @param request
     * @param auth：动态查找已认证和未认证的企业账号（1---已认证，2---未认证）
     * @return
     */
    @GetMapping("/getApplyEntAccountList.do/{pageNum}/{auth}")
    public ServerResponse<PageInfo> getApplyEntAccountList(@PathVariable("pageNum") Integer pageNum, @PathVariable("auth") int auth, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为管理员 (roleId == 4)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    pageNum = pageNum == null ? 1 : pageNum;
                    return adminManageService.getApplyEntAccountList(pageNum, auth);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }


    /**
     * 查看企业账号认证信息
     * @param userid 企业账号Id
     * @param request
     * @return
     */
    @GetMapping("/getEntAccountAuthInfo.do/{userid}")
    public ServerResponse<User> getEntAccountAuthInfo(@PathVariable("userid") Integer userid, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为管理员 (roleId == 4)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    return adminManageService.getEntAccountAuthInfo(userid);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }

    /**
     * 审批企业账号
     * @param entUserId
     * @param request
     * @param approve ：1---通过认证； 2---拒绝认证
     * @return
     */
    @PostMapping("/approveEnterpriseAccount.do/{entUserId}/{approve}")
    public ServerResponse<String> approveEnterpriseAccount(@PathVariable("entUserId") Integer entUserId,
                                                   @PathVariable("approve") Integer approve,
                                                   HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为管理员 (roleId == 4)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    return adminManageService.approveEnterpriseAccount(entUserId, approve);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }


    /**
     * 获取所有用户
     * @param userSearchVo：动态获取
     * @param request
     * @return
     */
    @PostMapping("/getAllUserList.do/{pageNum}")
    public ServerResponse<PageInfo> getAllUserList(@Validated @RequestBody UserSearchVo userSearchVo, @PathVariable("pageNum") Integer pageNum, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为管理员 (roleId == 4)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    pageNum = pageNum == null ? 1 : pageNum;
                    return adminManageService.getAllUserList(userSearchVo, pageNum);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }

    /**
     * 启用、禁用账号
     * @param userId 管理的用户Id
     * @param status 1---启用，2---禁用。
     * @param request
     * @return
     */
    @PostMapping("/manageAccount.do/{userId}/{status}")
    public ServerResponse<String> manageAccount(@PathVariable("userId") Integer userId, @PathVariable("status") Integer status, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为管理员 (roleId == 4)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    return adminManageService.mangeAccount(userId, status);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }


    /**
     * 获取公告列表
     * @param announceSearchVo：动态搜索
     * @param request
     * @return
     */
    @PostMapping("/getAllAnnounceList.do/{pageNum}")
    public ServerResponse<PageInfo> getAllAnnounceList(@Validated @RequestBody AnnounceSearchVo announceSearchVo, @PathVariable("pageNum") Integer pageNum, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为管理员 (roleId == 4)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    pageNum = pageNum == null ? 1 : pageNum;
                    return adminManageService.getAllAnnounceList(announceSearchVo, pageNum);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }

    /**
     * 发布/ 更新公告
     * @param announce
     * @param request
     * @return
     */
    @PostMapping({"/addOrUpdateAnnounce.do/{announceId}", "/addOrUpdateAnnounce.do"})
    public ServerResponse<AnnounceListVo> addOrUpdateAnnounce(@Validated @RequestBody Announce announce, @PathVariable(value = "announceId", required = false) Integer announceId, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为管理员 (roleId == 4)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    return adminManageService.addOrUpdateAnnounce(announce, announceId, user);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }


    /**
     * 删除公告
     * @param announceId
     * @param request
     * @return
     */
    @PostMapping("/deleteAnnounce.do/{announceId}")
    public ServerResponse<String> deleteAnnounce(@PathVariable("announceId") Integer announceId, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为管理员 (roleId == 4)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    return adminManageService.deleteAnnounce(announceId);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }


    /**
     * 获取所有招聘信息列表
     * @param request
     * @return
     */
    @PostMapping("/getPositionList.do/{pageNum}")
    public ServerResponse<PageInfo> getPositionList(@PathVariable("pageNum") Integer pageNum, @Validated @RequestBody CompanyPositionSearchVo employmentSearchVo, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为管理员 (roleId == 4)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    return adminManageService.getPositionList(employmentSearchVo, pageNum);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }


    /**
     * 封禁/解封 招聘信息
     *
     * @param positionId
     * @param status： 0---封禁，1---正常(解封)
     * @param request
     * @return
     */
    @PostMapping("/managePosition.do/{positionId}/{status}")
    public ServerResponse<String> forbiddenPosition(@PathVariable("positionId") Integer positionId, @PathVariable("status") Integer status,HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为管理员 (roleId == 4)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    return adminManageService.forbiddenPosition(positionId, status);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }

    /**
     *
     * @param userName
     * @return
     */
    @GetMapping({"/getUserListByUserName.do/{userName}"})
    public ServerResponse getUserListByUserName(
            @PathVariable(value = "userName") String userName, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 判断角色是否为管理员 (roleId == 4)
                boolean isPermitted = false;
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_admin.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                // 如果有权限
                if (isPermitted) {
                    return adminManageService.getUserListByUserName(userName, user);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }

}
