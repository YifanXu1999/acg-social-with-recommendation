package com.acgsocial.user.service;

import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.models.dto.user.UserLoginDto;
import com.acgsocial.user.domain.dto.AccountConnectRequest;
import com.acgsocial.user.domain.dto.EmailSignUpRequest;
import com.acgsocial.user.domain.entity.User;

public interface UserAuthService {

    ResponseResult login(UserLoginDto userSignInDto);

    User signUp(EmailSignUpRequest emailSignUpRequest);

    void connectAccount(AccountConnectRequest accountConnectRequest);
}
