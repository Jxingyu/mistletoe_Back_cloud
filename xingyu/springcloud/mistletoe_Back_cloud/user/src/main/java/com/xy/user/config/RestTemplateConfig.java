package com.xy.user.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@Configuration// Spring框架里 随便写一个类 贴上@Configuration 标签  就作为Bean文件给spring扫描到
public class RestTemplateConfig {
    @Bean  // 启动时 执行一次 方法返回值就是一个Bean 就到IOC容器里去
    @LoadBalanced // 启用负载均衡
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IRule iRule() {
        // 将负载均衡策略配置为一个bean 就可以按策略访问多个集群
//        return new RandomRule();//随机策略
        return new RoundRobinRule();//轮循策略
//        return new WeightedResponseTimeRule();//权重策略
    }
}
