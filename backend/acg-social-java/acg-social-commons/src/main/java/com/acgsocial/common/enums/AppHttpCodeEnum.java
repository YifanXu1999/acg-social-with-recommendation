package com.acgsocial.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppHttpCodeEnum {
    // Success Code = 200
    SUCCESS(200,"Success"),
    // Login Code = 1~50
    User_LOGIN_Required(1,"Login Required"),
    User_LOGIN_PASSWORD_ERROR(2,"Login Password Error"),
    User_NOT_EXIST(3,"User Not Exist"),
    User_EXIST(4,"User Already Created"),

    // Access Controll = 400-499
    ACCESS_DENIED(403,"Access Denied"),
    NOT_FOUND(404,"404 Resource Not Found"),
    METHOD_NOT_ALLOWED(415, "Request method, not supported"),

    // TOKEN = 50~100
    TOKEN_INVALID(50,"Invalid Token"),
    TOKEN_EXPIRED(51,"Token Expired"),
    TOKEN_REQUIRE(52,"Token Required"),
    // Sign = 100~120
    SIGN_INVALID(100,"Invalid Signing"),
    SIG_TIMEOUT(101,"Signing Expired"),
    // Parameters 500~1000
    PARAM_REQUIRE(500,"Missing Parameters"),
    PARAM_INVALID(501,"Parameters Invalid"),

    // Server Error 503
    SERVER_ERROR(503,"Internal Server Error"),
    // Data =  1000~2000
    DATA_EXIST(1000,"Data Duplicate Exception"),

    // Authroization =  3000~3500
    NO_OPERATOR_AUTH(3000,"Not Authorized"),
    NEED_ADMIND(3001,"Required Admin");

    final int code;

    final String message;

}