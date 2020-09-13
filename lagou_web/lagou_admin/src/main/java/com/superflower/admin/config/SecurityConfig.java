package com.superflower.admin.config;

import com.superflower.admin.security.*;
import com.superflower.admin.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.filter.CorsFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private GatherUrlRoles gatherUrlRoles;

    @Autowired
    private MyAccessDecisionManager accessDecisionManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminService).passwordEncoder(new BCryptPasswordEncoder());
        /*
         * 张三他有   用户管理（菜单） -- icon url
         *               用户查看（按钮）
         *                   查看API（功能） -- api
         *               用户修改（按钮）
         *                   修改API（功能）
         *               用户删除（按钮）
         *                   删除API（功能）
         *               用户添加（按钮）
         *                   添加API（功能）
         * */
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    // 将自己的授权鉴权功能替换上去
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(gatherUrlRoles);
                        o.setAccessDecisionManager(accessDecisionManager);
                        return o;
                    }
                })
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .addFilter(new JwtLoginFilter(super.authenticationManager()))
                .addFilter(new JwtVerifyFilter(super.authenticationManager()))
                .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class);
        // 【未登录】访问需要认证或权限的借口时响应内容
        http.exceptionHandling().authenticationEntryPoint(new LoginVerifyFailHandler());
        // 【登录过后】访问无权限的接口时自定义403响应内容
        http.exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler());
    }
}
