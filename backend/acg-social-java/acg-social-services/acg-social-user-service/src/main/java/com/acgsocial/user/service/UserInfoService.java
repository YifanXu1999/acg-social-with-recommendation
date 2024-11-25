package com.acgsocial.user.service;

import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.models.dto.user.UserLoginDto;
import com.acgsocial.models.dto.user.UserSignUpDto;

public interface UserInfoService  {

    ResponseResult login(UserLoginDto userSignInDto);

    ResponseResult signUp(UserSignUpDto userSignUpDto);
}
