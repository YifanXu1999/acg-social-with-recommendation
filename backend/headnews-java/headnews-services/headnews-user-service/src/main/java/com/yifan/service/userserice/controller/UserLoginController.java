package com.yifan.service.userserice.controller;

import com.yifan.service.userserice.model.dto.LoginDto;
import com.yifan.service.userserice.service.CUserDetailService;
import com.yifan.utils.jwt.JwtUtilService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UserLoginController {

    private final AuthenticationManager authenticationManager;


    private JwtUtilService jwtUtil;

    public UserLoginController(AuthenticationManager authenticationManager, JwtUtilService jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public String login(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        String userName = authentication.getName();
        String token = jwtUtil.generateToken(new User(userName, "", authentication.getAuthorities()));

        return token;
    }
}
