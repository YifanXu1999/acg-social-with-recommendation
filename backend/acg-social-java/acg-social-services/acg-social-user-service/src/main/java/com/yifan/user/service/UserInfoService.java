package com.yifan.user.service;

import com.yifan.common.result.ResponseResult;
import com.yifan.models.dto.user.UserLoginDto;
import com.yifan.models.dto.user.UserSignUpDto;

public interface UserInfoService  {

    ResponseResult login(UserLoginDto userSignInDto);

    ResponseResult signUp(UserSignUpDto userSignUpDto);
}
