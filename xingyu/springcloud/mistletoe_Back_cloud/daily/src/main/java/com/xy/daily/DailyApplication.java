package com.xy.daily;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableDiscoveryClient// 通用
@MapperScan({"com.xy.daily.mapper", "com.xy.mapper"})
@ComponentScan(basePackages = {"com.xy.daily", "com.xy"})
//@RibbonClient(name = "permission",configuration = MayRule.class)
//@EnableFeignClients(basePackages = "com.xy.daily.feign")
//@EnableHystrix
//@EnableScheduling //开启SpringTask的定时任务功能
public class DailyApplication {
    public static void main(String[] args) {
        SpringApplication.run(DailyApplication.class, args);
    }
}
