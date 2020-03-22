package com.zzj.recruitment.controller;

import com.zzj.recruitment.bo.CaptchaEntity;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.service.ICaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-15 13:32:46
 */
@RestController
@Slf4j
public class KaptchaController {



    @Autowired
    ICaptchaService captchaService;


    @GetMapping("/captcha/{username}")
    public ServerResponse<CaptchaEntity> captcha(@PathVariable String username) {
        try {
            ServerResponse<CaptchaEntity> response = captchaService.createToken(username);
            if (response.isSuccess()) {
                return response;
            }
        } catch (IOException e) {
            log.warn("生成验证码图片出现异常！", e);
        }
        return ServerResponse.createResponseByErrorMsg("生成验证码失败");
    }
}
