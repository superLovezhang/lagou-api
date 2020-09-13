package com.superflower.admin.security;

import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问url没有权限时 调用的handler
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        JwtLoginFilter.res(response, R.error(StatusCode.NOT_HAVE_ACCESS));
    }
}
