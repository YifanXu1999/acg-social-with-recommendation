package com.yifan.blogpost.service.controller;

import com.yifan.blogpost.service.producer.RocketMQProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rocketmq")
public class RocketMQController {

    @Autowired
    private RocketMQProducerService producerService;

    @GetMapping("/send/{topic}")
    public String sendMessage(@PathVariable String topic) {
        producerService.sendMessage(topic, "Hello, RocketMQ!");
        return "Message sent successfully!";
    }
}
