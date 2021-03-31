//package com.xy.config;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class RabbitMqConfig {
//    @Value("${queue.loginLog.name}")
//    private String LoginLogQueue;
//
//    //队列
//    @Bean
//    public Queue LoginLogQueue() {
//        return new Queue(LoginLogQueue, true);
//    }
//
//    //交换机
//    @Bean
//    public DirectExchange LogExchange() {
//        return new DirectExchange("LogExchange", true, false);//durable 开启持久化 autoDelete 自动删除
//    }
//
//    //绑定LoginLogQueue队列 到 LogExchange交换机
//    @Bean
//    public Binding LoginLogQueueToLogExchange() {
//        return BindingBuilder.bind(LoginLogQueue()).to(LogExchange()).with("#.LoginLog.#");//with(路由键)
//    }
//}
