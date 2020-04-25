package com.zzj.recruitment.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资格证书
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-18 13:25:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeCertificationListVo {

    private Integer id;

    /**
     * 证书名称
     */
    private String certificationName;

}
