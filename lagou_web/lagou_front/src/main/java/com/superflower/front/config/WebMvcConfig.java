package com.superflower.front.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


/**
 * 直接继承WebMvcConfigurationSupport 会将mvc的所有默认配置清楚      不安全
 * 实现WebMvcConfigurer 配置的进行应用 没有配置的使用默认             安全
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer/* extend WebMvcConfigurationSupport */ {
    @Autowired
    private GlobalInterceptor globalInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor).excludePathPatterns("/user/login", "/user/register", "/user/sendCode");
    }
}
