package com.acgsocial.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate a =  new RestTemplate();
        String url = "http://localhost:9989/realms/acg-social/protocol/openid-connect/certs";
        String result = a.getForObject(url, String.class);
        System.out.println(result);
        return a;
    }
}