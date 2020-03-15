package com.zzj.recruitment.controller;

import com.zzj.recruitment.dao.UserMapper;
import com.zzj.recruitment.util.EncryptionUtil;
import com.zzj.recruitment.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-08 23:41:42
 */
@RestController
@Slf4j
public class test {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private PropertiesUtil propertiesUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/admin/hello")
    public String Hello() {
        stringRedisTemplate.opsForValue().set("a","zzj");
        return "hello";
    }
}
