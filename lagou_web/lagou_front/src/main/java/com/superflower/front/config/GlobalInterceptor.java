package com.superflower.front.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import com.superflower.common.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Component
public class GlobalInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            String token = authorization.replace("Bear ", "");
            boolean checkToken = JwtUtils.checkToken(token);
            // token检验不通过 返回错误结果
            if (!checkToken) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=utf8");
                PrintWriter writer = response.getWriter();
                R r = R.error(StatusCode.LOGIN_NOT_VALID);
                ObjectMapper mapper = new ObjectMapper();
                writer.write(mapper.writeValueAsString(r));
                writer.flush();
                writer.close();
                return false;
            }
            // 通过 给请求头添加用户信息
            Map map = JwtUtils.decode(token);
            request.setAttribute("username", map.get("username"));
            request.setAttribute("id", map.get("id"));
        }
        return true;
    }
}
