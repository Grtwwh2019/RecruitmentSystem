package com.zzj.recruitment.filters;

import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.util.CookieUtil;
import com.zzj.recruitment.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-04-27 19:03:20
 */
@Slf4j
@Component
@WebFilter(filterName="SessionExpireFilter",urlPatterns="/*")
public class SessionExpireFilter implements Filter {

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);

        if(StringUtils.isNotEmpty(loginToken)){
            //判断logintoken是否为空或者""；
            //如果不为空的话，符合条件，继续拿user信息

            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if(user != null){
                //如果user不为空，则重置session的时间，即调用expire命令
                redisTemplate.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
                log.info("用户状态：在线！");
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
