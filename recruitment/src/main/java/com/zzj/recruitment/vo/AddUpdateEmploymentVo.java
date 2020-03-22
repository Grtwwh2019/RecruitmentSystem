package com.zzj.recruitment.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 20:23:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUpdateEmploymentVo {



    private Integer id; // 招聘信息Id，后端自动注入

    @NotBlank(message = "职位名称不能为空！")
    private String ename;

    @NotNull(message = "职位类型不能为空！")
    private Integer etypeid;

    @NotNull(message = "工作城市不能为空！")
    private String ecityid;

    @NotNull(message = "最低薪资不能为空！")
    private BigDecimal esalaryfloor;

    @NotNull(message = "最高薪资不能为空！")
    private BigDecimal esalaryceilie;

    @NotNull(message = "学历要求不能为空!")
    private Integer educationrequire;

    private Integer ecompanyid; // 后端自动注入

    @NotBlank(message = "岗位职责不能为空！")
    private String eresponsibility;

    @NotBlank(message = "岗位要求不能为空！")
    private String erequirement;

    private Integer epublisher; // 后端自动注入

    private Integer eupdater; // 修改时后端自动注入

    /**
     * 状态（0——封禁，1——正常）,默认为1
     */
    private Integer estatus; // 后端自动注入

    private Date createtime; // 发布时间，后端自动注入

    private Date updatetime; // 修改时间，后端自动注入
}
