package com.zzj.recruitment.controller.Backend;

import com.zzj.recruitment.common.ServerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-22 15:30:48
 */
@RestController
@RequestMapping("/admin")
public class AdminManageController {


    @GetMapping("getCompanyList.do")
    public ServerResponse getCompanyList() {
        return null;
    }
}
