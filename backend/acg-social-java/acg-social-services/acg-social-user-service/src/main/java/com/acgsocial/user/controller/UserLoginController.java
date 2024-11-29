package com.acgsocial.user.controller;

import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.user.domain.dto.EmailLoginRequest;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "User Authentication Controller", description = "Controller for user authentication operations")
public class UserLoginController {

    private final UserAuthService userAuthService;

    @PostMapping("/login")
    @Operation(summary = "User Sign In", description = "Authenticate user and return JWT token")
    public ResponseResult login(
      @Parameter(description = "User Sign In Data Transfer Object", required = true)
      @RequestBody EmailLoginRequest emailLoginRequest) {
        return ResponseResult.success(userAuthService.loginWithEmail(emailLoginRequest));
    }



}