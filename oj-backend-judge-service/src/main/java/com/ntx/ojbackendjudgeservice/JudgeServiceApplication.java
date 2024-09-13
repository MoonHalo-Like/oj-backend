package com.ntx.ojbackendjudgeservice;

import com.ntx.ojbackendjudgeservice.rabbitmq.InitRabbitMq;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.ntx")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.ntx.ojbackendserviceclient.service"})
public class JudgeServiceApplication {

    public static void main(String[] args) {
        InitRabbitMq.doInit();
        SpringApplication.run(JudgeServiceApplication.class, args);
    }

}
