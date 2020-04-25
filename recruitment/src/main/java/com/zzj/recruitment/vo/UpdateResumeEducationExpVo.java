package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzj.recruitment.customValid.CheckWorkDateInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-20 20:46:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@CheckWorkDateInterval(startdate = "startdate", enddate = "enddate", message = "开始日期不能大于结束日期！")
public class UpdateResumeEducationExpVo {

    private Integer id;

    private Integer resuid;

    /**
     * 学校Id
     */
    @NotNull(message = "学校不能为空")
    private Integer schoolId;

    /**
     * 学校类型，0---全日制，1---非全日制
     */
    private Integer schoolType;

    /**
     * 学历 1----初中及以下，2---中专/中技，3---高中，4---大专，5---本科，6---硕士，7---博士
     */
    @NotNull(message = "学历不能为空")
    private Integer degree;

    /**
     * 专业
     */
    @NotBlank(message = "专业不能为空")
    private String major;

    /**
     * 入学时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    private Date startdate;

    /**
     * 毕业时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @NotNull(message = "结束日期不能为空")
    private Date enddate;

    /**
     * 学校经历
     */
    private String schoolexp;
}
