package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzj.recruitment.customValid.CheckWorkDateInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
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

    @Min(value = 0, message = "职位Id参数出错。")
    private Integer employmentId; // 职位Id (职位搜索)

    @Min(value = 0, message = "公司Id参数出错。")
    private Integer companyId; // 公司Id (职位搜索)

    private String ename; // 职位名字

    private String cname; // 公司名字

    private String city;//工作地点

    @Min(value = 0, message = "学历参数出错。")
    private Integer education; // 学历 (职位搜索)

    @Min(value = 0, message = "公司id参数出错。")
    private Integer ecompanyId; // 公司id (职位搜索)

    @Min(value = 0, message = "公司规模参数出错。")
    private Integer csize; // 公司规模

    @Min(value = 0, message = "行业参数出错。")
    private Integer industry; // 行业

    @Min(value = 0, message = "融资阶段参数出错。")
    private Integer finacing; // 融资阶段

    @Min(value = 0, message = "职位类型参数出错。")
    private Integer etypeid; // 职位类型

    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTImeFrom;

    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTImeTo;

    private Integer publisherId;

    /**
     * 状态（0——封禁，1——正常）
     */
    private Integer estatus;
}
