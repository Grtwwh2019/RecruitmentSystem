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
 * since 2020-03-25 22:09:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@CheckWorkDateInterval(startdate = "createTImeFrom", enddate = "createTImeTo", message = "开始时间不能大于结束时间！")
public class AnnounceSearchVo {

    @Min(value = 1, message = "id参数出错。")
    private Integer id;

    private String title;

    @Min(value = 0, message = "类型参数出错。")
    private Integer type;

    @Min(value = 1, message = "发送者参数出错。")
    private Integer sender;

    @Min(value = 1, message = "接受者参数出错。")
    private Integer receiver;

    @Min(value = 0, message = "状态参数出错。")
    private Integer status;

    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTImeFrom;

    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTImeTo;
}
