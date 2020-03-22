package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 项目经历
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-18 13:07:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumeProjectExpListVo {

    private Integer id;

//    private Integer resuid;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 担任角色
     */
    private String role;

    /**
     * 项目链接
     */
    private String url;

    /**
     * 开始时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date starttime;

    /**
     * 结束时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endtime;

    /**
     * 项目描述
     */
    private String description;


}
