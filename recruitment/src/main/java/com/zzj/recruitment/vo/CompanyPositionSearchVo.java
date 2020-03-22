package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzj.recruitment.customValid.CheckWorkDateInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 14:18:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@CheckWorkDateInterval(startdate = "createTImeFrom", enddate = "createTImeTo", message = "开始时间不能大于结束时间！")
public class CompanyPositionSearchVo {

    private String ename; // 职位名字

    private String cname; // 公司名字

    private String city;//工作地点

    private Integer education; // 学历 (职位搜索)

    private Integer csize; // 公司规模

    private Integer industry; // 行业

    private Integer finacing; //融资阶段

    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTImeFrom;

    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTImeTo;

    private Integer publisherId;
}
