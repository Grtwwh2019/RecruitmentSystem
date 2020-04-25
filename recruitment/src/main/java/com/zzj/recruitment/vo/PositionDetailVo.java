package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 15:56:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionDetailVo {

    private Integer id;

    /**
     * 职位名称
     */
    private String ename;

    /**
     * 职位类型--->positionTypeSet
     */
    private String etype;

    /**
     * 职位类型Id--->positionTypeSet
     */
    private Integer etypeid;

    /**
     * 工作城市
     */
    private String ecity;

    /**
     * 工作城市id
     */
    private String ecityid;

    /**
     * 最低薪资
     */
    private BigDecimal esalaryfloor;

    /**
     * 最高薪资
     */
    private BigDecimal esalaryceilie;

    /**
     * 学历要求
     */
    private Integer educationrequire;

    /**
     * 公司Id
     */
    private Integer ecompanyId;

    /**
     * 公司名称
     */
    private String ecompany;

    /**
     * 公司Logo
     */
    private String companyLogo;

    /**
     * 职位职责
     */
    private String eresponsibility;

    /**
     * 职位要求
     */
    private String erequirement;

    /**
     * 发布人
     */
    private String publisher;

    /**
     * 发布时间
     */
    private Date createtime;

    /**
     * 状态（0——封禁，1——正常）
     */
    private Integer estatus;
}
