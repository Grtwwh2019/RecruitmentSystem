package com.zzj.recruitment.service;

import com.zzj.recruitment.bo.CaptchaEntity;
import com.zzj.recruitment.common.ServerResponse;

import java.io.IOException;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-15 13:43:25
 */
public interface ICaptchaService {

    ServerResponse<CaptchaEntity> createToken() throws IOException;

    void deleteToken(String token);
}
