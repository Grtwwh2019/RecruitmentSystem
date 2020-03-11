package com.zzj.recruitment.controller;

import com.zzj.recruitment.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-08 23:41:42
 */
@RestController
public class test {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/hello")
    public String Hello() {
        return "hello";
    }
}
