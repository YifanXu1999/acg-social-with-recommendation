package com.acgsocial.user.controller;

import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.user.domain.dto.EmailLoginRequest;
import com.acgsocial.user.domain.dto.Oauth2LoginRequest;
import com.acgsocial.user.domain.vo.AuthTokenResponse;
import com.acgsocial.user.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "User Authentication Controller", description = "Controller for user authentication operations")
public class UserAccountController {

    private final UserAuthService userAuthService;

    @PostMapping("/login/email")
    @Operation(summary = "User Sign In", description = "Authenticate user and return JWT token")
    public ResponseResult<AuthTokenResponse> login(
      @Parameter(description = "User Sign In Data Transfer Object", required = true)
      @RequestBody EmailLoginRequest emailLoginRequest) {
        return ResponseResult.success(userAuthService.loginWithEmail(emailLoginRequest));
    }

    @PostMapping("/login/oauth2")
    public ResponseResult<AuthTokenResponse> login(@RequestBody Oauth2LoginRequest oauth2LoginRequest) {
        log.info("Oauth2 Login Request: {}", oauth2LoginRequest);
        return ResponseResult.success(userAuthService.loginWithOauth2(oauth2LoginRequest));
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }



}