package com.xy.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableDiscoveryClient// 通用
@MapperScan({"com.xy.user.mapper","com.xy.mapper"})
@ComponentScan(basePackages = {"com.xy.user", "com.xy"})
//@RibbonClient(name = "permission",configuration = MayRule.class)
@EnableFeignClients(basePackages = "com.xy.user.feign")
@EnableHystrix
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
