package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 职位信息列表
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-15 23:50:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PosisitonListVo {

    private Integer id;

    /**
     * 职位名字
     */
    private String ename;

    /**
     * 职位类型
     */
    private String etype;

    /**
     * 工作城市
     */
    private String ecity;

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
     * 公司Logo
     */
    private String companyLogo;


    /**
     * 职位所属企业
     */
    private String ecompany;

    /**
     * 行业
     */
    private String industry;

    /**
     * 融资阶段（1——未融资 2——天使轮 3——A轮 4——B轮 5——C轮 6——D轮及以上 7——上市公司 8——不需要融资）
     */
    private Integer financing;

    /**
     * 公司规模（1——少于15人 2——15-50人 3——50-150人 4——150-500人 5——500-2000人 6——2000人以上）
     */
    private Integer csize;

    /**
     * 发布人
     */
    private String publisher;


    /**
     * 发布时间
     */
    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    /**
     * 状态（0——封禁，1——正常）
     */
    private Integer estatus;




}
