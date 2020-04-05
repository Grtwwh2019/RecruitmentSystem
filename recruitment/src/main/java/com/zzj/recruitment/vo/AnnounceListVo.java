package com.zzj.recruitment.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-17 01:34:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnnounceListVo {

    private Integer id;

    private String title;

    private String content;

    private Integer type;

    private String sender;

    private Integer receiver;

    private String receiverName;

    private Integer status;

    private Date createtime;

//    private Date updatetime;
}
