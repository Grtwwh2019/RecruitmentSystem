package com.zzj.recruitment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-04-27 19:10:51
 */
@Configuration
public class FilterConfig implements WebMvcConfigurer {



//    @Bean
//    public FilterRegistrationBean sessionExpireFilter() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new SessionExpireFilter());
//        registration.addUrlPatterns("/*"); // 配置过滤规则
//        registration.setName("SessionExpireFilter"); //设置过滤器名称
//        registration.setOrder(1); //执行次序
//        return registration;
//    }
}
