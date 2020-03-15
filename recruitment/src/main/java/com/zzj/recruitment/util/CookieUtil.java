package com.zzj.recruitment.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 操作Cookie的封装工具类
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-14 22:23:34
 */
@Slf4j
public class CookieUtil {


    private final static String COOKIE_DOMAIN = ".grtwwh.com";
    private final static String COOKIE_NAME = "grtwwh_login_token";


    /**
     * 获取Cookie
     * @param request
     * @return
     */
    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck : cks){
                log.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    log.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    //X:domain=".baidu.com"
    //a:A.baidu.com            cookie:domain=A.baidu.com;path="/"
    //b:B.baidu.com            cookie:domain=B.baidu.com;path="/"
    //c:A.baidu.com/test/cc    cookie:domain=A.baidu.com;path="/test/cc"
    //d:A.baidu.com/test/dd    cookie:domain=A.baidu.com;path="/test/dd"
    //e:A.baidu.com/test       cookie:domain=A.baidu.com;path="/test"

    /**
     * 将Cookie写回给客户端
     * @param response
     * @param token
     */
    public static void writeLoginToken(HttpServletResponse response,String token){
        Cookie ck = new Cookie(COOKIE_NAME,token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");//代表设置在根目录
        ck.setHttpOnly(true);
        //单位是秒。
        //如果这个maxage不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        ck.setMaxAge(60 * 60 * 24 * 365);//如果是-1，代表永久
        log.info("write cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
        response.addCookie(ck);
    }


    /**
     * 删除Cookie
     * @param request
     * @param response
     */
    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck : cks){
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//设置成0，代表删除此cookie。
                    log.info("del cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }
}
