package com.yifan.userserice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorld {

    @Value("${dataMessage}")
    private String dataMessage;
    @GetMapping
    public String hello() {
        return dataMessage + " Hesllo orl";
    }
}
