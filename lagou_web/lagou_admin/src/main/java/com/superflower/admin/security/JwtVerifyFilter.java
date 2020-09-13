package com.superflower.admin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superflower.admin.entity.Admin;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import com.superflower.common.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtVerifyFilter extends BasicAuthenticationFilter {

    public JwtVerifyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 从请求中获取要验证的token
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bear")) {
            // 验证通过调用FilterChain的doFilter方法 让这次请求进入下一个拦截器中 并且把登录用户的信息放进security的上下文中
            String token = authorization.replace("Bear ", "");
            if (JwtUtils.checkToken(token)) {
                Admin admin = new ObjectMapper().readValue((String) JwtUtils.decode(token).get("admin"), Admin.class);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(admin.getAccount(), null, admin.getRoles());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                chain.doFilter(request, response);
                return;
            }
        } else {
            // 压根没有信息 要求登录
//            JwtLoginFilter.res(response, R.error(StatusCode.LOGIN_NOT));
            // 放行静态资源 就打开这行
            chain.doFilter(request, response);
            return;
        }
        //验证不通过 直接响应错误结果
        JwtLoginFilter.res(response, R.error(StatusCode.LOGIN_NOT_VALID));
    }
}
