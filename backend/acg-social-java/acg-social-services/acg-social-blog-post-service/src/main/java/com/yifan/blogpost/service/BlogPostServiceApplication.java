package com.yifan.blogpost.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.yifan")
@EnableJpaRepositories("com.yifan.models")
public class BlogPostServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogPostServiceApplication.class, args);
    }
}
