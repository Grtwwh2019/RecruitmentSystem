package com.zzj.recruitment.controller.Backend;

import com.github.pagehelper.PageInfo;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.service.Backend.IUserManageService;
import com.zzj.recruitment.util.CookieUtil;
import com.zzj.recruitment.vo.AnnounceListVo;
import com.zzj.recruitment.vo.EnterpriseUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-15 19:03:46
 */
@RestController
@RequestMapping("/backend/user")
public class UserManageController {

    @Autowired
    IUserManageService userManageService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 收藏/取消收藏职位
     *
     * @return
     * @Param type：（收藏=1，取消收藏=0）
     */
    @PostMapping("/collectPosition.do")
    public ServerResponse collectPosition(Integer positionId, Integer type, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 已登录
                ServerResponse response = userManageService.collectPosition(positionId, user.getId(), type);
                return response;
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }

    /**
     * 收藏/取消收藏公司
     *
     * @return
     * @Param type：（收藏=1，取消收藏=0）
     */
    @PostMapping("/collectCompany.do")
    public ServerResponse collectCompany(Integer companyId, Integer type, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 已登录
                ServerResponse response = userManageService.collectCompany(companyId, user.getId(), type);
                return response;
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }


    /**
     *
     * @param type:0——职位，1——公司
     * @param pageNum:页码，也就是第几页
     * @param pageSize：页面容量，也就是一页有多少条数据
     * @return
     */
    @GetMapping(value = {"/getCollectionList.do/{type}/{pageNum}/{pageSize}", "/getCollectionList.do/{type}",
            "/getCollectionList.do/{type}/{pageNum}", "/getCollectionList.do/{type}/{pageSize}"})
    public ServerResponse<PageInfo> getCollectionList(
            @PathVariable(value = "type") Integer type,
            @PathVariable(value = "pageNum", required = false) Integer pageNum,
            @PathVariable(value = "pageSize", required = false) Integer pageSize,
            HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                ServerResponse<PageInfo> collectionList = userManageService.returnCollectionList(type, user, pageNum, pageSize);
                return collectionList;
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }


    /**
     * getAnnounceList
     * @return
     */
    @GetMapping(value = {"/getAnnounceList.do/{pageNum}", "/getAnnounceList.do"})
    public ServerResponse<PageInfo> getAnnounceList(@PathVariable(value = "pageNum", required = false) Integer pageNum, HttpServletRequest request){
        // todo 以后改为json存储具体内容
        if (pageNum == null) {
            pageNum = 1;
        }
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                ServerResponse response = userManageService.returnAnnounceList(user, pageNum);
                return response;
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }


    /**
     * 根据 id 获取公告详情
     *
     * @param announceId
     * @return
     */
    @GetMapping("/getAnnounceDetail.do/{announceId}")
    public ServerResponse<AnnounceListVo> getAnnounceDetail(@PathVariable Integer announceId, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                ServerResponse response = userManageService.returnAnnounceDetail(announceId, user);
                return response;
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录");
    }


    /**
     * 提交企业用户认证申请
     *  companyId 所属公司id
     *  empNo 工号
     *  cardPhoto 工牌
     *  enterpriseMail 企业邮箱
     * @return
     */
    @PostMapping("/applyEnterpriseUser.do")
    public ServerResponse applyEnterpriseUser(@Validated @RequestBody EnterpriseUserInfoVo enterpriseUserInfoVo, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 点击上传工牌以后，得到返回的工牌uri--->cardPhoto，然后带着这四个参数申请认证
                return userManageService.applyEnterpriseUser(enterpriseUserInfoVo, user);
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录");
    }
}
