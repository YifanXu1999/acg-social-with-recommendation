package com.yifan.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CacheServiceApplication {
    public static void main(String[] args) {
        // Your code here
        SpringApplication.run(CacheServiceApplication.class, args);

    }
}