package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzj.recruitment.customValid.CheckSalary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 更新简历基本信息的Vo
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-18 21:21:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@CheckSalary(lowsalary = "lowsalary", highsalary = "highsalary", message = "要求最低薪资必须小于要求最高薪资！")
public class UpdateResumeBaseInfoVo {

    // 简历Id
    private Integer id;

    // 简历所属用户
    private Integer ruserid;

    private String headermask;

    @NotBlank(message = "姓名不能为空！")
    private String realname;

    private Integer gender;

    @NotNull(message = "出生日期不能为空！")
    @Past(message = "出生日期不能超过当前日期！")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date birthday;

    private Integer currentstatus;

    private String advantage;

    private String socialpage;

    private String otherattachment;

    private String resumeattachment;

    private Integer expectpositionid;

    @NotNull(message = "薪资不能为空！")
    private BigDecimal lowsalary;

    @NotNull(message = "薪资不能为空！")
    private BigDecimal highsalary;

    private List<Integer> industryid;

    private String cityid;

}
