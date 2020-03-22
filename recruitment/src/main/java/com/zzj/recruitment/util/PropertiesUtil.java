package com.zzj.recruitment.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-11 19:12:57
 */
@Configuration
@ConfigurationProperties(prefix = "custom")
@Data
public class PropertiesUtil {

    private String EncryptionKey;

    private String ftp_server_ip;

    private String ftp_user;

    private String ftp_pass;

    private String ftp_server_http_prefix;


}
