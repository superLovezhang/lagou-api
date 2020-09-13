package com.superflower.front;

import com.superflower.common.config.CommonImportSelector;
import com.superflower.common.config.SwaggerConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.superflower.front.mapper") // 指定mapper包权限定名
@Import(CommonImportSelector.class) // 引入无法扫描到位置的配置类
@EnableTransactionManagement // 开启事务支持
@EnableAspectJAutoProxy // 开启aop支持
@EnableCaching(proxyTargetClass = true) // 开启缓存支持
public class FrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrontApplication.class, args);
    }
}
