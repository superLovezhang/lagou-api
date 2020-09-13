package com.superflower.admin.security;

import com.superflower.admin.entity.Permission;
import com.superflower.admin.entity.Role;
import com.superflower.admin.service.IPermissionService;
import com.superflower.admin.service.IRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 该类会在访问需要权限的URL时调用 获取当前URL所需要的角色列表
 */
@Component
public class GatherUrlRoles implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    /**
     * 该方法要求返回角色集合 return null 了之后直接放行该URL 不会再经过decide方法判断了
     *
     * @param object http请求
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 如果用户角色为超级管理员 直接放行 最大权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<Role> list = (List<Role>) authentication.getAuthorities();
            if (list.size() != 0 && "超级管理员".equals(list.get(0).getRoleName())) return null;
        }
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        // 对请求url进行处理
        requestUrl = processUrl(requestUrl);
        System.out.println("*****************************本次访问URI为" + requestUrl + "****************************");
        Permission permission = permissionService.getOneByUrl(requestUrl);
        // 判断当前url是否在资源表中 不在就放行
        if (permission == null) return null;
        List<Role> roleList = rolePermissionService.ListByPermissionId(permission.getPermissionId());
        // 如果当前资源从未被分配给角色上 随便返回一个永远猜不到的角色名 使其没有权限
        if (roleList == null || roleList.size() == 0) return SecurityConfig.createList("DONT_HAVE_ROLE");
        ArrayList<String> roleNames = new ArrayList<>();
        for (Role role : roleList) {
            roleNames.add(role.getRoleName());
        }
        return SecurityConfig.createList(roleNames.toArray(new String[roleNames.size()]));
    }

    private String processUrl(String requestUrl) {
        // 如果url带参 去掉问号及其以后的
        int i = requestUrl.indexOf("?");
        if (i != -1) {
            requestUrl = requestUrl.substring(0, i);
        }
        // 如果带数字 就去掉数字和前面的/
        Pattern p = Pattern.compile(".*\\d+.*");
        if (p.matcher(requestUrl).find()) {
            String str = requestUrl.split("\\d")[0];
            return str.substring(0, str.length() - 1);
        }
        return requestUrl;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
