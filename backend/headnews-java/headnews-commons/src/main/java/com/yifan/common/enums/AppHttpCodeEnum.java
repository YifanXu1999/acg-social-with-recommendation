package com.yifan.common.enums;

public enum AppHttpCodeEnum {
    // Success Code = 200
    SUCCESS(200,"Success"),
    // Login Code = 1~50
    LOGIN_Required(1,"Login Required"),
    LOGIN_PASSWORD_ERROR(2,"Login Password Error"),
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
    USER_DATA_NOT_EXIST(1001,"User Not Exist"),
    // Authroization =  3000~3500
    NO_OPERATOR_AUTH(3000,"Not Authorized"),
    NEED_ADMIND(3001,"Required Admin");

    int code;

    String errorMessage;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}