package com.yifan.user.service.controller;

import com.yifan.common.result.ResponseResult;
import com.yifan.models.dto.user.UserSignInDto;
import com.yifan.models.dto.user.UserSignUpDto;
import com.yifan.user.service.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class UserLoginController {

    private final UserInfoService userInfoService;



    @PostMapping("/signin")
    public ResponseResult login(@RequestBody UserSignInDto loginDto) {
        return userInfoService.signIn(loginDto);
    }

    @PostMapping("/signup")
    public ResponseResult signup(@RequestBody UserSignUpDto signUpDto) {
        return userInfoService.signUp(signUpDto);
    }
}
