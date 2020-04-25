package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-16 20:42:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CompanyListVo {

    private Integer id;

    private String logo;

    private String cname;

//    private Integer productid;

    /**
     * 融资阶段（0——未融资 1——天使轮 2——A轮 3——B轮 4——C轮 5——D轮及以上 6——上市公司 7——不需要融资）
     */
    private Integer financing;

    /**
     * 行业
     */
    private String industry;

    /**
     * 公司规模（1——少于15人 2——15-50人 3——50-150人 4——150-500人 5——500-2000人 6——2000人以上）
     */
    private Integer csize;

    private String ccity;

    /**
     * 代表法人
     */
    private String representative;

}
