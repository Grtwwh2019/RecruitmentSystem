package com.zzj.recruitment.controller.Backend;

import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.service.Backend.IResumeService;
import com.zzj.recruitment.service.IFileService;
import com.zzj.recruitment.util.CookieUtil;
import com.zzj.recruitment.util.PropertiesUtil;
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
 * since 2020-03-17 21:39:17
 */
@RestController
@RequestMapping(("/resume"))
public class ResumeController {

    @Autowired
    private PropertiesUtil propertiesUtil;

    @Autowired
    private IResumeService resumeService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IFileService fileService;


    /**
     * 查看在线简历
     * @param request
     * @return
     */
    @GetMapping("/getResumeInfo.do")
    public ServerResponse<ResumeInfoVo> getResumeInfo(HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                return resumeService.returnResumeInfo(user);
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录！");
    }

    /**
     * 编辑在线简历基本信息
     * 更新：必须校验传递的简历的所属用户是登录用户的
     * 更新：上传简历附件
     *       当调用上传文件接口/common/upload.do以后，返回一个"uri": "1ddf11ac-2bbd-4a83-817e-43ab555f6598.jpg",
     *       然后前端带着uri组成UpdateResumeBaseInfoVo进行更新简历
     * @return
     */
    @PostMapping("/updateResumeBaseInfo.do")
    public ServerResponse<ResumeBaseInfoVo> updateResumeBaseInfo(@Validated @RequestBody UpdateResumeBaseInfoVo resume, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                return resumeService.updateResumeBaseInfo(resume, user);
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录!");
    }


    /**
     * 添加/更新简历工作经历信息
     * workExpId存在则更新，否则新增
     * @param workExpId
     * @param request
     * @return
     */
    @PostMapping({"/updateResumeWorkExpInfo.do", "/updateResumeWorkExpInfo.do/{workExpId}"})
    public ServerResponse<ResumeWorkExpListVo> updateResumeWorkExpInfo(@PathVariable(value = "workExpId", required = false) Integer workExpId, @Validated @RequestBody UpdateResumeWorkExpVo workExpVo, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                return resumeService.updateResumeWorkExpInfo(workExpId, workExpVo, user);
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录!");
    }


    /**
     * 添加/更新简历项目经历信息
     * projectExpId存在则更新，否则新增
     * @param projectExpId
     * @param projectExpVo
     * @param request
     * @return
     */
    @PostMapping({"/updateResumeProjectExpInfo.do", "/updateResumeProjectExpInfo.do/{projectExpId}"})
    public ServerResponse<ResumeProjectExpListVo> updateResumeProjectExpInfo(@PathVariable(value = "projectExpId", required = false) Integer projectExpId, @Validated @RequestBody UpdateResumeProjectExpVo projectExpVo, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                return resumeService.updateResumeProjectExpInfo(projectExpId, projectExpVo, user);
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录!");
    }


    /**
     * 添加/更新简历教育经历信息
     * educationExpId存在则更新，否则新增
     * @param educationExpId
     * @param educationExpVo
     * @param request
     * @return
     */
    @PostMapping({"/updateResumeEducationExpInfo.do", "/updateResumeEducationExpInfo.do/{educationExpId}"})
    public ServerResponse<ResumeEducationExpListVo> updateResumeEducationExpInfo(@PathVariable(value = "educationExpId", required = false) Integer educationExpId, @Validated @RequestBody UpdateResumeEducationExpVo educationExpVo, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                return resumeService.updateResumeEducationExpInfo(educationExpId, educationExpVo, user);
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录!");
    }


    /**
     * 更新简历资格证书
     * @param certificationIdList
     * @param request
     * @return
     */
    @PostMapping({"/updateResumeCertificationListInfo.do"})
    public ServerResponse<List<ResumeCertificationListVo>> updateResumeCertificationInfo(@RequestBody List<Integer> certificationIdList, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                return resumeService.updateResumeCertificationInfo(certificationIdList, user);
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录!");
    }


    /**
     * 删除附件
     *
     * @param request
     * @param type : 简历附件， 其他附件
     * @return
     */
    @PostMapping("/deleteAttachment.do")
    public ServerResponse deleteAttachment(String type, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                ServerResponse<String> response = resumeService.deleteAttachment(type, user);
                if (response.isSuccess()) {
                    Boolean result = fileService.delete(response.getData(), "attachment");
                    if (result) {
                        return response;
                    }
                }
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录!");
    }


    /**
     * 获取附件下载链接
     * @param type
     * @param request
     * @return
     */
    @PostMapping("/downloadAttachment.do")
    public ServerResponse downloadAttachment(String type, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                String fileName = "";
                // todo 从数据库返回fileName，然后判断FTP中是否存在该文件
                // todo 如果存在，直接拼接url
                // 拼接url,前端拿到url就可以直接使用了
                String url = propertiesUtil.getFtp_server_http_prefix() + fileName;
                return ServerResponse.createResponseBySuccess("获取url成功！", url);
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录!");
    }





}
