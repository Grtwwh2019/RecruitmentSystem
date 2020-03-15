package com.zzj.recruitment.config.Security;

/*
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzj.recruitment.common.ServerResponse;
import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.pojo.User;
import com.zzj.recruitment.service.impl.UserServiceImpl;
import com.zzj.recruitment.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
*/

import com.zzj.recruitment.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-12 21:08:39
 * @EnableGlobalMethodSecurity ：注解表示开启方法安全的控制
 */
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig
//        extends WebSecurityConfigurerAdapter
{

    /**
     * 角色继承
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_admin > ROLE_enterAccount \n ROLE_admin > ROLE_enterUser \n ROLE_enterUser > ROLE_user";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
    /**
     * Spring Security提供的自带加盐加密工具
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    UserServiceImpl userService;

    @Autowired
    SecurityFilter securityFilter;

    @Autowired
    SecurityAccessDecisionManager securityAccessDecisionManager;

    @Autowired
    RedisTemplate redisTemplate;

   /* @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (req, resp, e) -> ServerResponse.createResponseByErrorCustom(resp.getStatus(), "您没有权限，登录失败！");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        *//**
         * 基于内存的认证, Spring5以后密码是要加密的，否则会出错。
         *//*
//        auth.inMemoryAuthentication()
//                .withUser("zzj").password("123").roles("user");
        auth.userDetailsService(userService);
    }

    *//**
     * 在这里可以配置拦截规则
     *
     * @param http
     * @throws Exception
     *//*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        *//**
        * authorizeRequests：开启配置
        * antMatchers：ant风格的匹配符，可以在这里设置匹配路径
        * hasAnyRole/hasRole：表示要访问匹配的路径必须具备的角色
        * anyRequest.authenticated()：表示剩余的其他请求在登录之后都能访问
        * formLogin：表示表单登录
        * .loginProcessingUrl()：处理登录的url，即处理登录请求的地址
        * .permitAll()：表示跟登录相关的接口直接就能访问
        * .csrf().disable()：关闭csrf攻击，方便使用postman进行测试
        * .successHandler()：登录成功的处理器，返回的是json格式，用于前后端分离的系统
        * .failureHandler()：登录失败的处理器
        * .logout()：注销登录
        * .logoutUrl()：处理注销登录的url
        * .logoutSuccessHandler：注销登录的处理器
        * .withObjectPostProcessor()：配置自定义的Filter类和AccessDecisionManager类
        *//*
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(securityAccessDecisionManager);
                        o.setSecurityMetadataSource(securityFilter);
                        return o;
                    }
                })
                .and()
                .formLogin()
                .loginProcessingUrl("/doLogin")
                .loginPage("/user/login.do")
                .successHandler(new AuthenticationSuccessHandler() {
                     *//**
                     * @param authentication : 保存的是登录成功的用户的信息
                     * @throws IOException
                     * @throws ServletException
                     *//*
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req,
                        HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                         *//**
                         * 在这里设置返回一段json
                         *//*
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        User user = (User) authentication.getPrincipal();
                        user.setPassword(null);
//                        String userInfo = JsonUtil.obj2String(user);
                        redisTemplate.opsForValue().set(Const.LOGINED_USER, user, Const.RedisCacheExtime.REDIS_SESSION_EXTIME, TimeUnit.SECONDS);
                        out.write(new ObjectMapper().writeValueAsString(ServerResponse.createResponseBySuccess("登录成功", user)));
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                     *//**
                     *
                     * @param e ：通过这个异常可以锁定出错的信息
                     * @throws IOException
                     * @throws ServletException
                     *//*
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp,
                            AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        ServerResponse<Object> responseByErrorMsg = null;
                        if (e instanceof LockedException) {
                            responseByErrorMsg = ServerResponse.createResponseByErrorMsg("账户被锁定，登录失败");
                        } else if (e instanceof BadCredentialsException) {
                            responseByErrorMsg = ServerResponse.createResponseByErrorMsg("用户名或密码输入错误，登录失败");
                        } else if (e instanceof DisabledException) {
                            responseByErrorMsg = ServerResponse.createResponseByErrorMsg("账户被禁用，登录失败");
                        } else if (e instanceof AccountExpiredException) {
                            responseByErrorMsg = ServerResponse.createResponseByErrorMsg("账户过期，登录失败");
                        } else if (e instanceof CredentialsExpiredException) {
                            responseByErrorMsg = ServerResponse.createResponseByErrorMsg("密码过期，登录失败");
                        }else {
                            responseByErrorMsg = ServerResponse.createResponseByErrorMsg("登录失败");
                        }
                        out.write(new ObjectMapper().writeValueAsString(responseByErrorMsg));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .logout() // 默认接口是 /logout
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp,
                                                Authentication authentication) throws IOException, ServletException {
                        *//**
                         * 注销以后将缓存中的用户信息清除
                         *//*
                        Boolean delete = redisTemplate.delete(Const.LOGINED_USER);
                        resp.setContentType("application/json;charset=uft-8");
                        ServerResponse<Object> responseBySuccessMsg = null;
                        PrintWriter out = resp.getWriter();
                        responseBySuccessMsg = delete ? ServerResponse.createResponseBySuccessMsg("注销登录成功")
                                : ServerResponse.createResponseByErrorMsg("注销登录失败");
                        out.write(new ObjectMapper().writeValueAsString(responseBySuccessMsg));
                        out.flush();
                        out.close();
                    }
                })
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 未登录时，在这里处理结果，不要重定向
                .csrf().disable().exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    }



    *//**
     * 忽略拦截:某一个请求地址不需要拦截
     * @param web
     * @throws Exception
     *//*
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/user/login.do", "/user/register.do", "/hello");
    }*/
}
