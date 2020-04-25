package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-04-19 16:36:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IndustryOrPositionSetListVo {

    private Integer value; // id

    private String label; // iname

    private List<IndustryOrPositionSetListVo> children;
}
