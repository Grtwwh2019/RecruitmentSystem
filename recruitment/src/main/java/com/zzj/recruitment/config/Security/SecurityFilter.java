package com.zzj.recruitment.config.Security;

import com.zzj.recruitment.common.constant.Const;
import com.zzj.recruitment.pojo.Right;
import com.zzj.recruitment.pojo.Role;
import com.zzj.recruitment.service.IRigthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-13 15:12:24
 */
@Component
public class SecurityFilter
//        implements FilterInvocationSecurityMetadataSource
{

    /**
     * 路径规则匹配符，直接跟数据库中的url匹配
     */
    AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    IRigthService rigthService;

    /**
     * 分析请求地址，确定请求地址需要的角色
     *
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    /*@Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl(); // 获取请求的地址
        List<Right> allRights = rigthService.getAllRights(); // 获取所有的权限资源
        // 判断请求地址能否跟权限资源地址匹配成功
        for (Right right : allRights) {
            if (pathMatcher.match(right.getUrl(), requestUrl)) {
                // 如果匹配成功，获取该权限资源所需要的角色
                List<Role> roles = right.getRoles();
                String[] rightStr = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    rightStr[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(rightStr);
            }
        }
        // 如果匹配失败，则要求进行登录
        return SecurityConfig.createList(Const.LOGIN_NEEDED);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }*/
}
