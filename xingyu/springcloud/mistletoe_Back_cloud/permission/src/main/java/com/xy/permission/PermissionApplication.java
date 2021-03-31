package com.xy.permission;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication//(scanBasePackages = "com.xy.service")
@EnableEurekaClient
//@EnableFeignClients(basePackages = {"com.wx.cloud.common"})
@MapperScan("com.xy.permission.mapper")
@ComponentScan(basePackages = {"com.xy"})
public class PermissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(PermissionApplication.class, args);
    }
}
