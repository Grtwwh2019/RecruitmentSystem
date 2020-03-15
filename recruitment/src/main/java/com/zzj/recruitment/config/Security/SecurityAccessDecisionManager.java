package com.zzj.recruitment.config.Security;

import com.zzj.recruitment.common.constant.Const;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-13 16:22:47
 */
@Component
public class SecurityAccessDecisionManager implements AccessDecisionManager {

    /**
     *
     * @param authentication ：保存当前登录成功以后的用户信息
     * @param o：filter类中的Object
     * @param collection：filter类中的Collection
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // 遍历collection，获得需要的角色
        for (ConfigAttribute attribute : collection) {
            if (attribute.getAttribute().equals(Const.LOGIN_NEEDED)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new AccessDeniedException("权限不足，请登录！");
                } else {
                    return;
                }
            }
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (attribute.getAttribute().equals(authority.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足，请登录！");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
