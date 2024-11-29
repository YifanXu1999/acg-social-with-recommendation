package com.acgsocial.user.service;

import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.models.dto.user.UserLoginDto;
import com.acgsocial.user.domain.dto.Oauth2AccountConnectRequest;
import com.acgsocial.user.domain.dto.EmailSignUpRequest;
import com.acgsocial.user.domain.dto.Oauth2AccountQueryRequest;
import com.acgsocial.user.domain.dto.Oauth2SignUpRequest;
import com.acgsocial.user.domain.entity.User;
import com.acgsocial.user.domain.entity.UserConnectedAccount;
import com.acgsocial.user.domain.vo.AuthTokenResponse;

public interface UserAuthService {

    ResponseResult login(UserLoginDto userSignInDto);

    User signUpWithEmail(EmailSignUpRequest emailSignUpRequest);

    User signUpWithOauth2(Oauth2SignUpRequest oauth2SignUpRequest);

    UserConnectedAccount findOauth2ConnectedAccount(Oauth2AccountQueryRequest accountQueryRequest);

    void connectWithOauth2(Oauth2AccountConnectRequest accountConnectRequest);


    AuthTokenResponse generatenNewAuthToken(User user);
}
