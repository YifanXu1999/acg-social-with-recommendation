package com.yifan.user.service.service;

import com.yifan.common.result.ResponseResult;
import com.yifan.models.dto.user.UserSignInDto;
import com.yifan.models.dto.user.UserSignUpDto;

public interface UserInfoService  {

    ResponseResult signIn(UserSignInDto userSignInDto);

    ResponseResult signUp(UserSignUpDto userSignUpDto);
}
