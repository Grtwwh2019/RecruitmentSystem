package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-22 18:05:12
 */
@Data
@AllArgsConstructor
@NotNull
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriseAccountListVo {

    /**
     * 用户的id
     */
    private Integer id;

    private String nickname;

    private String username;

    private String telephone;

    private String email;

    private String companyName;

    private Integer authentication;

}
