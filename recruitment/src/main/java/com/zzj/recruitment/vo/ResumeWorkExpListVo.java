package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作经历
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-18 12:46:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumeWorkExpListVo {

    // work experience list

    private Integer id;

//    private Integer resuid;

    /**
     * 公司名称
     */
    private String cname;

    /**
     * 公司所属行业， key:100002, value:"游戏"
     */
    private Integer industryId;
    private String industryName;
//    private Map<Integer, String> industry;

    /**
     * 所在部门
     */
    private String department;

    /**
     * 工作内容
     */
    private String workcontent;

    /**
     * 职位名称
     */
    private String position;

    /**
     * 职位id
     */
    private Integer positionId;

    /**
     * 职位类型名字
     */
    private String positionTypeZh;

    /**
     * 技能标签
     */
    List<Map<String,Object>> skillTags;

    /**
     * 开始日期
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startdate;

    /**
     * 结束日期
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date enddate;

}
