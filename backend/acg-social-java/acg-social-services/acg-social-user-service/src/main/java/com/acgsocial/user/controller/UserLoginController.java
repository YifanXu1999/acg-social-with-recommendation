package com.acgsocial.user.controller;

import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.models.dto.user.UserLoginDto;
import com.acgsocial.models.dto.user.UserSignUpDto;
import com.acgsocial.user.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
@Tag(name = "User Authentication Controller", description = "Controller for user authentication operations")
public class UserLoginController {

    private final UserInfoService userInfoService;

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
      @RequestBody UserSignUpDto signUpDto) {
        return userInfoService.signUp(signUpDto);
    }


}