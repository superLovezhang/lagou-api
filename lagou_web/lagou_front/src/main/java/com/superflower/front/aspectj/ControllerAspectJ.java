package com.superflower.front.aspectj;

import com.superflower.common.entity.vo.R;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class ControllerAspectJ {
    @Pointcut("execution(* com.superflower.front.controller.*.*(..))")
    public void pointcut() {}


//    @Before("pointcut()")
//    public void beforeMethod() {
//        System.out.println("我是前置环绕");
//    }

    @Around("pointcut() && args(request,..)")
    public R beforeMethod(ProceedingJoinPoint pjp, HttpServletRequest request) throws Throwable {
        String id = (String) request.getAttribute("id");
        if (StringUtils.isEmpty(id)) throw new RuntimeException("未登录");

        // 获取被通知的方法
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        // 获取当前方法的所有参数名称
        String[] names = signature.getParameterNames();
        // 将其转换成集合类型
        List<String> params = Arrays.asList(names);

        // 获取所有参数值
        Object[] args = pjp.getArgs();
        // 找出userId所在参数列表的位置
        int position = params.indexOf("userId");

        args[position] = id;

        R r = (R) pjp.proceed(args);
        return r;
    }
}
