package com.zzj.recruitment.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-15 13:57:53
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaEntity {

    private String captchaImg; // 图片 base64
    private String token; // UUID

}
