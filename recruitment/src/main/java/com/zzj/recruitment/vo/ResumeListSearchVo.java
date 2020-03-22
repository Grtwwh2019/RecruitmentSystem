package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.Date;

/**
 * 求职者简历的查找条件
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 23:09:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeListSearchVo {

    /**
     * 岗位Id
     */
    @Min(value = 1,message = "该参数必须为数字！")
    private Integer employmentId;

    /**
     * 求职者姓名
     */
    private String jobberRealName;

    /**
     * 求职者性别 （1---男，2---女）
     */
    @Min(value = 1, message = "该参数必须为数字！")
    private Integer gender;

    /**
     * 学历 1----初中及以下，2---中专/中技，3---高中，4---大专，5---本科，6---硕士，7---博士
     */
    @Min(value = 1,message = "该参数必须为数字！")
    private Integer education;



    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTImeFrom;


    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTImeTo;
}
