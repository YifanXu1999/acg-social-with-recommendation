package com.acgsocial.conrtoller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/")
@Slf4j
public class KeyCloakController {

    @GetMapping("/callback")
    public String home() {
        return "Welcome to the Keycloak Server callback!";
    }

    @GetMapping("/token")
    public Jwt getToken(@AuthenticationPrincipal Jwt jwt) {
        log.info("jwt: {}", jwt);
        return jwt;
    }




}
