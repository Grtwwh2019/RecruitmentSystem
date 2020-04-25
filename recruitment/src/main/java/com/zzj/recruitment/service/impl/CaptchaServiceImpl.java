package com.zzj.recruitment.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.zzj.recruitment.bo.CaptchaEntity;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.service.ICaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * version 2.0 ：修复 多个用户获取同一个验证码的情况
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
     * captcha：新生成的验证码内容
     * base64encode：图片 base64（显示验证码图片）
     *
     * @return
     */
    public ServerResponse<CaptchaEntity> createToken() throws IOException {
        CaptchaEntity tokenEntity = new CaptchaEntity();
        //生成一个token:UUID
        String token = UUID.randomUUID().toString();
        // 生成文字验证码(实际验证码内容，存在redis，不返回给前端)
        String captchaText = producer.createText();
        // 生成图片验证码（返回给前端）
        ByteArrayOutputStream outputStream = null;
        BufferedImage image = producer.createImage(captchaText);
        outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 这里需要用replace过滤一下，否则图片会转换失败
        String base64encode = encoder.encode(outputStream.toByteArray())
                .replace("\n", "").replace("\r", "");
        tokenEntity = new CaptchaEntity();
        tokenEntity.setCaptchaImg(base64encode);
        tokenEntity.setToken(token);
        // token存储的是验证码的实际内容
        redisTemplate.opsForValue().set(token, captchaText, 60 * 5, TimeUnit.SECONDS);
        return ServerResponse.createResponseBySuccess("获取验证码成功", tokenEntity);
    }

    public ServerResponse<CaptchaEntity> createToken(String username) throws IOException {

        // 判断缓存是否存在
        CaptchaEntity tokenEntity = (CaptchaEntity) redisTemplate.opsForValue().get(Const.CAPTCHA_ENTITY + username);

//        String token = (String) redisTemplate.opsForValue().get(Const.TOKEN_CAPTCHA_ENTITY);

        //判断是否生成过token：redis中的实际验证码内容(captchaText)
        String token = tokenEntity == null ? null : tokenEntity.getToken();
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
        // 要返回给用户的：验证码图片和token，用户输入验证码时带上这个token，后端根据这个token来获取验证码内容
        redisTemplate.opsForValue().set(Const.CAPTCHA_ENTITY + username, tokenEntity, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set(Const.TOKEN_CAPTCHA_ENTITY, token, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
        // token存储的是验证码的实际内容
        redisTemplate.opsForValue().set(token, captchaText, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
        return ServerResponse.createResponseBySuccess("获取验证码成功", tokenEntity);
    }

    @Override
    public void deleteToken(String captchaToken) {
        Boolean deleteResult = redisTemplate.delete(captchaToken);
        if (!deleteResult) {
            log.warn("删除验证码token失败");
        }
        log.info("删除验证码token成功");
    }

    @Override
    public void deleteToken(String token, String username) {
        Boolean deleteResult = redisTemplate.delete(token);
        if (!deleteResult) {
            log.warn("删除验证码token失败");
        }
        log.info("删除验证码token成功");
        deleteResult = redisTemplate.delete(Const.CAPTCHA_ENTITY + username);
        if (!deleteResult) {
            log.warn("删除" + username + "的验证码CAPTCHA_ENTITY失败");
        }
        log.info("删除" + username + "的验证码CAPTCHA_ENTITY成功");
//        deleteResult = redisTemplate.delete(Const.TOKEN_CAPTCHA_ENTITY);
//        if (!deleteResult) {
//            log.warn("删除验证码TOKEN_CAPTCHA_ENTITY失败");
//        }
    }

}
