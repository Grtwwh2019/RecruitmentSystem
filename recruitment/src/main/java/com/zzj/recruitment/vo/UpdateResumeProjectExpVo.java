package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzj.recruitment.customValid.CheckWorkDateInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-20 18:56:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@CheckWorkDateInterval(startdate = "starttime", enddate = "endtime", message = "开始日期必须小于结束日期！")
public class UpdateResumeProjectExpVo {

    private Integer id;

    private Integer resuid;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空")
    private String projectName;

    /**
     * 担任角色
     */
    @NotBlank(message = "担任角色不能为空")
    private String role;

    /**
     * 项目链接
     */
    private String url;

    /**
     * 开始时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @NotNull(message = "开始时间不能为空")
    private Date starttime;

    /**
     * 结束时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @NotNull(message = "结束时间不能为空")
    private Date endtime;

    /**
     * 项目描述
     */
    @NotBlank(message = "项目描述不能为空")
    @Length(max = 600)
    private String description;


}
