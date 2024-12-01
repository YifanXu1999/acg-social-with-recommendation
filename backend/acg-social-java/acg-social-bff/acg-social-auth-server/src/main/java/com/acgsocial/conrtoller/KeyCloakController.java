package com.acgsocial.conrtoller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
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
    public Jwt getToken(Authentication authentication) {
        log.info(authentication.getAuthorities().toString());
        log.info(authentication.getPrincipal().toString());
        return (Jwt) authentication.getPrincipal();
    }




}
