package com.superflower.admin.security;

import com.superflower.admin.entity.Role;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * 当访问需要权限的URL时 决定是否能够访问
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    /**
     * 抛出异常代表无法访问 否则就是可以访问
     *
     * @param authentication   封装的登录用户信息
     * @param object           http请求
     * @param configAttributes 当前URL需要的角色列表
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 放行静态资源 就打开这一段代码
        if (authentication instanceof AnonymousAuthenticationToken) throw new AccessDeniedException("未登录");
        List<Role> authorities = (List<Role>) authentication.getAuthorities();
        for (ConfigAttribute attribute : configAttributes) {
            String roleName = attribute.getAttribute();
            for (Role authority : authorities) {
                if (roleName.equals(authority.getRoleName())) return;
            }

        }
        // 当前用户没有URL需要的角色 抛出异常
        throw new AccessDeniedException("没有权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
