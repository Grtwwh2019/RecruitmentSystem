package com.zzj.recruitment.common.constant;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-11 16:54:54
 */
public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    FORCE_LOGIN(10, "FORCE_LOGIN"),
    UNKNOWN_ERROR(40, "UNKNOWN_ERROR");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
