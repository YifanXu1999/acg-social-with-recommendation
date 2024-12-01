package com.acgsocial.user.service.impl;

import com.acgsocial.common.enums.AppHttpCodeEnum;
import com.acgsocial.common.exception.CustomException;
import com.acgsocial.user.domain.dto.*;
import com.acgsocial.user.domain.entity.UserConnectedAccount;
import com.acgsocial.user.domain.enums.Oauth2ProviderEnum;
import com.acgsocial.user.domain.vo.AuthTokenResponse;
import com.acgsocial.user.domain.vo.Oauth2AccountDetailResponse;
import com.acgsocial.user.repository.UserConnectedAccountRepo;
import com.acgsocial.user.repository.UserRepo;
import com.acgsocial.user.service.UserAuthService;
import com.acgsocial.user.util.oauth2.Oauth2HandlerFactory;
import com.acgsocial.utils.jwt.JwtUtilService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.acgsocial.user.domain.entity.User;

/**
 * Implementation of the UserInfoService interfaces.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthServiceImpl implements UserAuthService, UserDetailsService {

    private final UserRepo userRepo;
    private final UserConnectedAccountRepo userConnectedAccountRepo;
    private final JwtUtilService jwtUtil;
    private final JwtUtilService jwtUtilService;
    private final UserDetailServiceImpl userDetailService;
    private final Oauth2HandlerFactory oauth2HandlerFactory;

    @Lazy
    @Autowired
    @Setter
    private  AuthenticationManager authenticationManager;


    @Override
    public  AuthTokenResponse loginWithEmail(EmailLoginRequest emailLoginRequest) {
        User user = loadUserByUsername(emailLoginRequest.getEmail());
        Authentication authentication = authenticationManager
          .authenticate(
            new UsernamePasswordAuthenticationToken(
                emailLoginRequest.getEmail(),
                emailLoginRequest.getPassword()
            )
          );
        if(authentication.isAuthenticated()) {
            return generatenNewAuthToken(user);
        } else {
            throw new CustomException(AppHttpCodeEnum.User_LOGIN_PASSWORD_ERROR);
        }
    }

    @Override
    public AuthTokenResponse loginWithOauth2(Oauth2LoginRequest oauth2LoginRequest) {
        // Retrieve the user information from the oauth2 account
        Oauth2ProviderEnum provider = oauth2LoginRequest.getProvider();
        Oauth2AccountDetailResponse oauth2AccountDetailResponse = oauth2HandlerFactory
                                                                    .getHandler(provider)
                                                                    .getAccountDetail(oauth2LoginRequest.getCode());
        Long providerId = oauth2AccountDetailResponse.getProviderId();
        // Check if the oauth2 account is already connected to a registered account.
        UserConnectedAccount connectedAccount = findOauth2ConnectedAccount(new Oauth2AccountQueryRequest(provider, providerId));
        // If user logged in with oauth2 is already connected
        if (connectedAccount != null) {
            log.info("User already connected with provider {} and providerId {}", provider, providerId);
            connectedAccount.getUser();
            return generatenNewAuthToken(connectedAccount.getUser());
        }
        // If user logged in with oauth2  is not connected to any account
        else {
            // Find or create a new user
            User user = userRepo
              // Find the user by email, if oauth2 account email is null, then pass an empty string
              .findByEmail(oauth2AccountDetailResponse.getEmail() == null ? "" : oauth2AccountDetailResponse.getEmail())
              // Create a new user object if the user does not exist
              .orElseGet(() -> {
                  Oauth2SignUpRequest signUpRequest = Oauth2SignUpRequest.builder()
                    .provider(provider.getProvider())
                    .providerId(providerId)
                    .email(oauth2AccountDetailResponse.getEmail())
                    .avatarUrl(oauth2AccountDetailResponse.getAvartarUrl())
                    .build();
                  return signUpWithOauth2(signUpRequest);
              });
            // Connect the oauth2 account to the user
            connectWithOauth2(new Oauth2AccountConnectRequest(provider.getProvider(), providerId, user));
            return generatenNewAuthToken(user);
        }

    }


    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo
          .findByEmail(email)
          .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User signUpWithOauth2(Oauth2SignUpRequest oauth2SignUpRequest) {
        // Create a new user object
        User user = new User(oauth2SignUpRequest);
        userRepo.save(user);
        return user;
    }

    @Override
    public UserConnectedAccount findOauth2ConnectedAccount(Oauth2AccountQueryRequest accountQueryRequest) {
        return userConnectedAccountRepo
          .findByProviderAndProviderId(accountQueryRequest.getProvider().getProvider(), accountQueryRequest.getProviderId())
          .orElse(null);
    }

    /**
     * Signs up a new user with the provided information.
     *
     * @param emailSignUpRequest the user sign-up data transfer object
     * @return ResponseResult containing the JWT token if registration is successful, or an error response
     */
    @Override
    public User signUpWithEmail(EmailSignUpRequest emailSignUpRequest) {
        // Check if the user already exists
        userRepo
            .findByEmail(emailSignUpRequest.getEmail())
            .ifPresent(userInfo -> {
                    throw new CustomException(AppHttpCodeEnum.User_EXIST);
              });
        // Create a new user  object
        User user = new User(emailSignUpRequest);
        userRepo.save(user);
        return user;
    }

    @Override
    public void connectWithOauth2(Oauth2AccountConnectRequest accountConnectRequest) {
        // Need to check if the account is already connected
        userConnectedAccountRepo
          .findByProviderAndProviderId(accountConnectRequest.getProvider(), accountConnectRequest.getProviderId())
          .ifPresent(userConnectedAccount -> {
                    throw new CustomException(AppHttpCodeEnum.User_ACCOUNT_ALREADY_CONNECTED);
          });
        UserConnectedAccount connectedAccount = new UserConnectedAccount(accountConnectRequest);
        userConnectedAccountRepo.save(connectedAccount);
        return;
    }

    @Override
    public AuthTokenResponse generatenNewAuthToken(User user) {
        String accessToken = jwtUtilService.generateAccessToken(user);
        String refreshToken = jwtUtilService.generateRefreshToken(user);
        return new AuthTokenResponse(accessToken, refreshToken);
    }




}