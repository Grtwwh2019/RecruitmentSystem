package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 基本信息
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-17 22:55:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumeBaseInfoVo {

    // 简历Id
    private Integer id;

    // base info

    /**
     * 所属用户
     */
//    private Integer ruserid;

    /**
     * 头像
      */
    private String headermask;

    /**
     * 真名
     */
    private String realname;

    private String telephone;

    private String email;

    private Integer gender;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date birthday;

    /**
     * 当前身份：1---学生，2---非学生
     */
    private Integer currentstatus;

    /**
     * 个人优势
     */
    private String advantage;

    /**
     * 个人主页
     */
    private String socialpage;

    /**
     * 作品/其他附件
     */
    private String otherattachment;

    /**
     * 简历附件
     */
    private String resumeattachment;


    // expect list

    /**
     * 期望职位Id
     */
    private Integer expectpositionId;

    /**
     * 期望职位
     */
    private String expectposition;

    /**
     * 要求最低薪资
     */
    private BigDecimal lowsalary;

    /**
     * 要求最高薪资
     */
    private BigDecimal highsalary;

    /**
     * 期望行业 (0<= industry <= 3)
     *  [{industryId: 100002, name: "游戏"}, {industryId: 100021, name: "计算机软件"}]
     *  key:100002, value:"游戏"
     */
    private List<Map<String, Object>> industries;

    /**
     * 期望行业：显示状态
     */
    private String industryDesc;


    /**
     * 期望城市
     */
    private String city;

    /**
     * 期望城市Id
     */
    private String cityid;


//    企业用户功能新增

    /**
     * 学历
     */
    private Integer education;

    /**
     * 招聘岗位id
     */
    private Integer employmentId;

    /**
     * 招聘岗位名称
     */
    private String employmentName;

    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date deliveryTime;

}
