package com.acgsocial.user.service;


import com.acgsocial.user.domain.dto.*;
import com.acgsocial.user.domain.entity.User;
import com.acgsocial.user.domain.entity.UserConnectedAccount;
import com.acgsocial.user.domain.vo.AuthTokenResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAuthService{

    AuthTokenResponse loginWithEmail(EmailLoginRequest emailLoginRequest);

    User signUpWithEmail(EmailSignUpRequest emailSignUpRequest);

    User signUpWithOauth2(Oauth2SignUpRequest oauth2SignUpRequest);

    UserConnectedAccount findOauth2ConnectedAccount(Oauth2AccountQueryRequest accountQueryRequest);

    void connectWithOauth2(Oauth2AccountConnectRequest accountConnectRequest);


    AuthTokenResponse generatenNewAuthToken(User user);
}
