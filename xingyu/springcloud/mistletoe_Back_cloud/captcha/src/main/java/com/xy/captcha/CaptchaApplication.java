package com.xy.captcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication//(scanBasePackages = "com.xy.service")
@EnableEurekaClient
public class CaptchaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaptchaApplication.class, args);
    }
}