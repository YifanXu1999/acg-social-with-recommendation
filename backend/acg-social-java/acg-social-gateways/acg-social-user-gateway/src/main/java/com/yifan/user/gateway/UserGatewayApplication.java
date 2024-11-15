package com.yifan.user.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.yifan")
public class UserGatewayApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(UserGatewayApplication.class, args);


    }


}