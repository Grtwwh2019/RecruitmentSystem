package com.zzj.recruitment.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-11 18:28:05
 */
@Component
public class EncryptionUtil {


    /**
     * 初始化配置类属性的获取
     */

 /*   @Autowired
    private PropertiesUtil propertiesUtil;
    private static PropertiesUtil propertiesUtils;


    @PostConstruct
    public void init() {
        propertiesUtils = this.propertiesUtil;
    }*/

    /**
     * Spring Security提供的密码加密的工具类
     */
    private static PasswordEncoder encoder = new BCryptPasswordEncoder(10);

    /**
     * 加密
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        return encoder.encode(password);
    }

    /**
     * 解密
     * @param encryptPassword
     * @param password
     * @return
     */
    public static boolean match(String password, String encryptPassword) {
        return encoder.matches(password, encryptPassword);
    }


}
