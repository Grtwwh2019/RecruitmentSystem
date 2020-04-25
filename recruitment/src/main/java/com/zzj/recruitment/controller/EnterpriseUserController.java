package com.zzj.recruitment.controller;

import com.github.pagehelper.PageInfo;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.pojo.Role;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.service.Backend.IEnterpriseUserService;
import com.zzj.recruitment.service.Backend.IResumeService;
import com.zzj.recruitment.service.IRoleService;
import com.zzj.recruitment.util.CookieUtil;
import com.zzj.recruitment.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 18:45:22
 */
@RestController
@RequestMapping("/entUser")
public class EnterpriseUserController {

    @Autowired
    IEnterpriseUserService enterpriseUserService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IResumeService resumeService;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     * 根据登录的企业用户，获取企业用户发布的招聘信息列表
     *
     * @param pageNum
     * @param request
     * @return
     */
    @PostMapping("/getEmploymentList.do/{pageNum}")
    public ServerResponse<PageInfo> getEmploymentList(
            @PathVariable(value = "pageNum", required = false) Integer pageNum,
            @Validated @RequestBody CompanyPositionSearchVo employmentSearchVo,
            HttpServletRequest request) {
        // todo 实现动态排序
        String loginToken = CookieUtil.readLoginToken(request);
        boolean isPermitted = false;
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_enterUser.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                if (isPermitted) {
                    // 符合权限
                    pageNum = pageNum == null ? 1 : pageNum;
                    return enterpriseUserService.getEmploymentListByUserId(pageNum, employmentSearchVo, user);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作，请先认证！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }


    /**
     * 发布招聘信息
     * @return
     */
    @PostMapping({"/addUpdateEmploymentInfo.do", "/addUpdateEmploymentInfo.do/{eid}"})
    public ServerResponse<PositionDetailVo> addUpdateEmploymentInfo(@Validated @RequestBody AddUpdateEmploymentVo addUpdateEmploymentVo, @PathVariable(value = "eid", required = false) Integer eid, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        boolean isPermitted = false;
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_enterUser.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                if (isPermitted) {
                    // 符合权限
                    eid = eid == null ? null : eid;
                    return enterpriseUserService.addOrUpdateEmployment(addUpdateEmploymentVo, eid, user);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作，请先认证！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }


    /**
     * 删除招聘信息
     * @param eid
     * @param request
     * @return
     */
    @PostMapping("/deleteEmploymentInfo.do/{eid}")
    public ServerResponse<String> deleteEmploymentInfo(@PathVariable("eid") Integer eid, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        boolean isPermitted = false;
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_enterUser.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                if (isPermitted) {
                    // 符合权限
                    return enterpriseUserService.deleteEmploymentInfo(eid);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作，请先认证！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }

    /**
     * 获取求职者简历列表
     * @param pageNum
     * @param resumeListSearchVo
     * @param request
     * @return
     */
    @PostMapping("/getUserResumeList.do/{pageNum}")
    public ServerResponse<PageInfo> getUserResumeList(
            @PathVariable(value = "pageNum", required = false) Integer pageNum,
            @Validated @RequestBody ResumeListSearchVo resumeListSearchVo,
            HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        boolean isPermitted = false;
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_enterUser.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                if (isPermitted) {
                    // 符合权限
                    pageNum = pageNum == null ? 1 : pageNum;
                    return enterpriseUserService.getUserResumeList(pageNum, resumeListSearchVo, user);
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作，请先认证！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }

    /**
     * 查看简历详情
     *
     * @param "resumeid": 2033, 根据简历Id找到用户id，根据用户id找到用户信息，根据用户信息，生成详细简历
     * @return
     */
    @GetMapping("/getResumeDetailInfo.do/{resumeId}/{employmentId}")
    public ServerResponse<ResumeInfoVo> getResumeDetailInfo(@PathVariable("resumeId") Integer resumeId, @PathVariable("employmentId") Integer employmentId, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        boolean isPermitted = false;
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                List<Role> roles = roleService.getAllRolesByUserId(user.getId());
                for (Role role : roles) {
                    if (role.getId() == Const.Role.ROLE_enterUser.getCode()) {
                        isPermitted = true;
                        break;
                    }
                }
                if (isPermitted) {
                    // 符合权限
                    ServerResponse<User> response = enterpriseUserService.getResumeDetailInfo(resumeId, employmentId, user);
                    if (response.isSuccess()) {
                        ServerResponse<ResumeInfoVo> response1 = resumeService.returnResumeInfo(response.getData());
                        return response1;
                    }
                    return ServerResponse.createResponseByErrorMsg("查询简历详情失败，您没有权限进行操作！");
                }
                return ServerResponse.createResponseByErrorMsg("您没有权限进行操作，请先认证！");
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }

}
