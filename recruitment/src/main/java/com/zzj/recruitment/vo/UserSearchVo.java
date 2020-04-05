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
 * since 2020-03-24 13:06:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@CheckWorkDateInterval(startdate = "createTImeFrom", enddate = "createTImeTo", message = "开始时间不能大于结束时间！")
public class UserSearchVo {

    @Min(value = 1, message = "id不能小于1。")
    private Integer id;

    private String nickname;

    private String telephone;

    private String email;

    private String username;

    @Min(value = 1, message = "公司参数出错。")
    private Integer companyid;

    @Min(value = 1, message = "工号不能小于1。")
    private Integer empno;

    @Min(value = 0, message = "认证情况参数出错。")
    private Integer authentication;

    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTImeFrom;

    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTImeTo;

}
