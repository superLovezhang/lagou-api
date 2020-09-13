package com.superflower.admin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superflower.admin.entity.Admin;
import com.superflower.admin.entity.Role;
import com.superflower.admin.entity.vo.AdminVo;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import com.superflower.common.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.setFilterProcessesUrl("/api/admin/login");
        this.authenticationManager = authenticationManager;
    }

    // 登录验证调用
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 获取请求体的数据
            AdminVo admin = new ObjectMapper().readValue(request.getInputStream(), AdminVo.class);
            // 将获取到的账号密码封装成UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(admin.getAccount(), admin.getPassword());
            // 最后交给authenticationManager去验证
            return authenticationManager.authenticate(token);
        } catch (Exception e) {
            // 获取请求体失败 给前端返回结果
            try {
                res(response, R.error(StatusCode.LOGIN_ERROR));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    // 登录成功调用
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 从Authentication中获取到登录成功用户的信息
        String account = authResult.getName();
        List<Role> roles = (List<Role>) authResult.getAuthorities();
        Admin admin = new Admin();
        admin.setAccount(account);
        admin.setRoles(roles);
        // 将其封装成jwt令牌 发给前端
        String token = JwtUtils.encode(new ObjectMapper().writeValueAsString(admin));
        res(response, R.success(StatusCode.LOGIN_SUCCESS, token));
    }

    // 登录失败调用
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        res(response, R.error(StatusCode.LOGIN_PASSWORD_ERROR));
    }

    public static void res(HttpServletResponse response, R success) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf8");
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(success));
        writer.flush();
        writer.close();
    }
}
