package com.xy.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.xy")
@EnableEurekaClient
@MapperScan("com.xy.security.mapper")
@ComponentScan(basePackages = {"com.xy.security", "com.xy"})//扫描器他模块的bean后 手动加上本类的bean
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}