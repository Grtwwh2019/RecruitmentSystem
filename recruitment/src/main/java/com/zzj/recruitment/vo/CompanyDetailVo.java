package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-21 16:04:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDetailVo {

    private Integer id;

    private String logo;

    private String cname;

    /**
     * 公司产品Id
     */
    // todo 以后扩展产品信息
//    private Integer productid;

    private String cintroduction;

    private Integer financing;

    private String industry;

    private Integer csize;

    private String ccityId;

    private String ccityName;

    private String fullname;

    private String representative;

    private BigDecimal assets;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd", locale = "zh")
    private Date establishtime;

    /**
     * 公司环境图片
     */
    private String photo;

    @JsonIgnore
    private Date createtime;
}
