package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 教育经历
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-18 13:00:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumeEducationExpListVo {

    private Integer id;

//    private Integer resumeid;

    /**
     * 学校Id
     */
    private Integer schoolId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 学校类型，0---全日制，1---非全日制
     */
    private Integer schoolType;

    /**
     * 学历 1----初中及以下，2---中专/中技，3---高中，4---大专，5---本科，6---硕士，7---博士
     */
    private Integer degree;

    /**
     * 专业
     */
    private String major;

    /**
     * 入学时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startdate;

    /**
     * 毕业时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date enddate;

    /**
     * 学校经历
     */
    private String schoolexp;

}
