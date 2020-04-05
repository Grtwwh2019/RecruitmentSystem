package com.zzj.recruitment.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-24 13:13:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailInfoVo {

    private Integer id;

    private String nickname;

    private String telephone;

    private String email;

    private String username;

    private Integer companyid;

    private String company;

    private Integer empno;

    private Integer authentication;

    private Date createtime;

}
