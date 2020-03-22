package com.zzj.recruitment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.service.ICommonService;
import com.zzj.recruitment.service.IFileService;
import com.zzj.recruitment.util.CookieUtil;
import com.zzj.recruitment.util.PropertiesUtil;
import com.zzj.recruitment.vo.CompanyPositionSearchVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-19 21:11:23
 */
@RestController()
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private PropertiesUtil propertiesUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    IFileService fileService;

    @Autowired
    ICommonService commonService;


    /**
     * 上传文件接口（头像、附件等等）
     * @param type : 可以是img，图片；也可以是attachment，附件；可扩展
     * @return
     */
    @PostMapping("/upload.do")
    public ServerResponse upload(@RequestParam(value = "uploadFile", required = false) MultipartFile file, @RequestParam(value = "type") String type, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 文件上传
                String path = request.getSession().getServletContext().getRealPath("upload");
                String targetFileName = fileService.upload(file, path, type);
                // 拼接url,前端拿到url就可以直接使用了
                String url = propertiesUtil.getFtp_server_http_prefix() + targetFileName;
                Map fileMap = Maps.newHashMap();
                fileMap.put("uri", targetFileName);
                fileMap.put("url", url);
                return ServerResponse.createResponseBySuccess("上传成功！",fileMap);
            }
        }
        return ServerResponse.createResponseByErrorMsg("您未登录，请先登录!");
    }


    /**
     * 富文本图片上传
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/richtextImgUpload.do")
    public Map richtextImgUpload(@RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user != null) {
                // 富文本中对于返回值有自己的要求，这里使用的是simditor（官网：simditor.tower.im）
////        {
////            "success": true/false,
////                "msg": "error message", # optional
////            "file_path": "[real file path]"
////        }
                String path = request.getSession().getServletContext().getRealPath("upload");
                String targetFileName = fileService.upload(file, path, "attachment");
                if (StringUtils.isBlank(targetFileName)) {
                    resultMap.put("success", false);
                    resultMap.put("msg", "富文本上传失败!");
                    return resultMap;
                }
                // 拼接url,前端拿到url就可以直接使用了
                String url = propertiesUtil.getFtp_server_http_prefix() + targetFileName;
                resultMap.put("success", true);
                resultMap.put("msg", "富文本上传成功!");
                resultMap.put("file_path", url);
                // 前端的约定Header
                response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
//                Map fileMap = Maps.newHashMap();
//                fileMap.put("uri", targetFileName);
//                fileMap.put("url", url);
                return resultMap;
            }
        }
        resultMap.put("success", false);
        resultMap.put("msg", "您未登录，请先登录!");
        return resultMap;
    }

    /**
     * 根据type查询数据，0——搜索职位；1——搜索公司
     *
     * @param type
     * @param searchVo
     * @return
     */
    @GetMapping({"/searchList.do/{type}/{pageNum}", "/searchList.do/{type}"})
    public ServerResponse<PageInfo> searchList(@PathVariable("type") Integer type, @PathVariable(value = "pageNum", required = false) Integer pageNum, @Validated @RequestBody CompanyPositionSearchVo searchVo) {
        if (pageNum == null) {
            pageNum = 1;
        }
        return commonService.returnListByType(type, searchVo, pageNum);
    }

    /**
     * 根据type查询详细数据，0——职位；1——公司
     *
     * @return
     */
    @GetMapping("/getDetail.do/{type}/{id}")
    public ServerResponse getDetail(@PathVariable("type") Integer type, @PathVariable("id") Integer id) {
        return commonService.getDetail(type, id);
    }

}
