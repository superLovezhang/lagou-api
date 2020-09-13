package com.superflower.admin.security;

import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 需要登录并且访问的url不在白名单调用的Handler
 */
public class LoginVerifyFailHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        JwtLoginFilter.res(response, R.error(StatusCode.LOGIN_NOT));
    }
}
