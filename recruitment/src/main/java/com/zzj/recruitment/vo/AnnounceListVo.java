package com.zzj.recruitment.vo;

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
public class AnnounceListVo {

    private Integer id;

    private String title;

    private String content;

    private Integer type;

    private String sender;

    private Integer receiver;

    private Integer status;

    private Date createtime;

//    private Date updatetime;
}
