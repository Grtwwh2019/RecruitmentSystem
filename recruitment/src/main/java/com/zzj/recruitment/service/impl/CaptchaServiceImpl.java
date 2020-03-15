package com.zzj.recruitment.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.zzj.recruitment.bo.CaptchaEntity;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.config.KaptchaConfig;
import com.zzj.recruitment.service.ICaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-15 13:43:34
 */
@Service("captchaService")
@Slf4j
public class CaptchaServiceImpl implements ICaptchaService {


    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    DefaultKaptcha producer;

    /**
     *
     * captcha：新生成的验证码内容
     * base64encode：图片 base64（显示验证码图片）
     * @return
     */
    @Override
    public ServerResponse<CaptchaEntity> createToken() throws IOException {

        CaptchaEntity tokenEntity = (CaptchaEntity) redisTemplate.opsForValue().get(Const.CAPTCHA_ENTITY);

        String token = (String) redisTemplate.opsForValue().get(Const.TOKEN_CAPTCHA_ENTITY);

        //判断是否生成过token：redis中的实际验证码内容
        String captchaText = token == null ? null : (String) redisTemplate.opsForValue().get(token);

        if (tokenEntity== null && token == null && captchaText == null) {
            //生成一个token:UUID
            token = UUID.randomUUID().toString();
            // 生成文字验证码(实际验证码内容，存在redis，不返回给前端)
            captchaText = producer.createText();


            // 生成图片验证码（返回给前端）
            ByteArrayOutputStream outputStream = null;
            BufferedImage image = producer.createImage(captchaText);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);

            // 对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();

            // 这里需要用replace过滤一下，否则图片会转换失败
            String base64encode = encoder.encode(outputStream.toByteArray()).replace("\n", "").replace("\r", "");
            tokenEntity = new CaptchaEntity();
            tokenEntity.setCaptchaImg(base64encode);
            tokenEntity.setToken(token);
        }
        redisTemplate.opsForValue().set(Const.CAPTCHA_ENTITY, tokenEntity, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(Const.TOKEN_CAPTCHA_ENTITY, token, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(token, captchaText, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
        return ServerResponse.createResponseBySuccess("获取验证码成功", tokenEntity);
    }

    @Override
    public void deleteToken(String token) {
        Boolean deleteResult = redisTemplate.delete(token);
        if (!deleteResult) {
            log.warn("删除验证码token失败");
        }
        deleteResult = redisTemplate.delete(Const.CAPTCHA_ENTITY);
        if (!deleteResult) {
            log.warn("删除验证码CAPTCHA_ENTITY失败");
        }
        deleteResult = redisTemplate.delete(Const.TOKEN_CAPTCHA_ENTITY);
        if (!deleteResult) {
            log.warn("删除验证码TOKEN_CAPTCHA_ENTITY失败");
        }
    }

}
