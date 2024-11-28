package com.acgsocial.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Map;

@RestController
@Slf4j
public class OAuth2CallbackController {

    @GetMapping("/callback")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        log.info("User: {}", principal);
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
}