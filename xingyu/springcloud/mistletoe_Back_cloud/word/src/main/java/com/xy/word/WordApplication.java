package com.xy.word;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableDiscoveryClient// 通用
@MapperScan({"com.xy.word.mapper", "com.xy.mapper"})
@ComponentScan(basePackages = {"com.xy.word", "com.xy"})
//@RibbonClient(name = "permission",configuration = MayRule.class
//@EnableFeignClients(basePackages = "com.xy.daily.feign")
//@EnableHystrix
//@EnableScheduling //开启SpringTask的定时任务功能
public class WordApplication {
    public static void main(String[] args) {
        SpringApplication.run(WordApplication.class, args);
    }
}
