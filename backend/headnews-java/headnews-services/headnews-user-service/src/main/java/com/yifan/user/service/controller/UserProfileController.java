package com.yifan.user.service.controller;

import com.yifan.common.result.ResponseResult;
import com.yifan.models.dto.user.UserSignInDto;
import com.yifan.models.dto.user.UserSignUpDto;
import com.yifan.user.service.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Tag(name = "User Profile Controller", description = "Controller for user profile operations")
public class UserProfileController {

    private final UserInfoService userInfoService;

    @PostMapping("/signin")
    @Operation(summary = "User Sign In", description = "Authenticate user and return JWT token")
    public ResponseResult login(
      @Parameter(description = "User Sign In Data Transfer Object", required = true)
      @RequestBody UserSignInDto loginDto) {
        return userInfoService.signIn(loginDto);
    }

    @PostMapping("/signup")
    @Operation(summary = "User Sign Up", description = "Register a new user and return JWT token")
    public ResponseResult signup(
      @Parameter(description = "User Sign Up Data Transfer Object", required = true)
      @RequestBody UserSignUpDto signUpDto) {
        return userInfoService.signUp(signUpDto);
    }
}