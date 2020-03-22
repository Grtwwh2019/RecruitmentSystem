package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-20 12:59:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseUserInfoVo {

    private Integer userId;

    private String nickname;

    private String telephone;

    @NotNull(message = "公司不能为空！")
    private Integer companyId;

    private String companyName; // 公司名称

    @NotNull(message = "员工号不能为空")
    private Integer empNo;

    @NotBlank(message = "工牌不能为空")
    private String cardPhoto;

    @NotBlank(message = "企业邮箱不能为空")
    @Email(message = "企业邮箱格式错误！")
    private String enterpriseMail;


}
