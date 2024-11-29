package com.acgsocial.user.controller;

import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.models.dto.user.UserLoginDto;
import com.acgsocial.user.domain.dto.EmailSignUpRequest;
import com.acgsocial.user.domain.entity.User;
import com.acgsocial.user.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "User Authentication Controller", description = "Controller for user authentication operations")
public class UserLoginController {

    private final UserAuthService userInfoService;

    @PostMapping("/login")
    @Operation(summary = "User Sign In", description = "Authenticate user and return JWT token")
    public ResponseResult login(
      @Parameter(description = "User Sign In Data Transfer Object", required = true)
      @RequestBody UserLoginDto loginDto) {
        return userInfoService.login(loginDto);
    }

    @PostMapping("/signup")
    @Operation(summary = "User Sign Up", description = "Register a new user and return JWT token")
    public ResponseResult signup(
      @Parameter(description = "User Sign Up Data Transfer Object", required = true)
      @RequestBody EmailSignUpRequest emailSignUpRequest) {
        User user = userInfoService.signUp(emailSignUpRequest);
        log.info("User signed up: {}", user);
        // TODO Return JWT token
        return ResponseResult.success(user);
    }


}