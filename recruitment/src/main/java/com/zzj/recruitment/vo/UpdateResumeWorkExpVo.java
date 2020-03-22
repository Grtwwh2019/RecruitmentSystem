package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzj.recruitment.customValid.CheckWorkDateInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-20 14:09:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@CheckWorkDateInterval(startdate = "startdate", enddate = "enddate", message = "开始日期不能大于结束日期！")
public class UpdateResumeWorkExpVo {

    private Integer id;

    // 所属简历Id
    @JsonIgnore
    private Integer resuid;

    /**
     * 公司名称
     */
    @NotBlank(message = "公司名称不能为空")
    private String cname;

    /**
     * 公司所属行业， key:100002, value:"游戏"
     */
    @NotNull(message = "公司行业不能为空")
    private Integer industryId;
//    private String industryName;
//    private Map<Integer, String> industry;

    /**
     * 所在部门
     */
    private String department;

    /**
     * 工作内容
     */
    @NotBlank(message = "工作内容不能为空")
    private String workcontent;

    /**
     * 职位名称
     */
    private String position;

    /**
     * 职位id
     */
    @NotNull(message = "职位类型不能为空")
    private Integer positionId;

    /**
     * 技能标签
     */
    private List<Integer> skillTags;

    /**
     * 开始日期
     */
    @NotNull(message = "开始日期不能为空")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startdate;

    /**
     * 结束日期
     */
    @NotNull(message = "结束日期不能为空")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date enddate;

}
