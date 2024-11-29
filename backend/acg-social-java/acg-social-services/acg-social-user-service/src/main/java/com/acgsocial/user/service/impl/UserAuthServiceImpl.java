package com.acgsocial.user.service.impl;

import com.acgsocial.common.enums.AppHttpCodeEnum;
import com.acgsocial.common.exception.CustomException;
import com.acgsocial.common.result.ResponseResult;
import com.acgsocial.models.dto.user.UserLoginDto;
import com.acgsocial.user.domain.dto.Oauth2AccountConnectRequest;
import com.acgsocial.user.domain.dto.EmailSignUpRequest;
import com.acgsocial.user.domain.dto.Oauth2AccountQueryRequest;
import com.acgsocial.user.domain.dto.Oauth2SignUpRequest;
import com.acgsocial.user.domain.entity.UserConnectedAccount;
import com.acgsocial.user.domain.vo.AuthTokenResponse;
import com.acgsocial.user.repository.UserConnectedAccountRepo;
import com.acgsocial.user.repository.UserRepo;
import com.acgsocial.user.service.UserAuthService;
import com.acgsocial.utils.jwt.JwtUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import com.acgsocial.user.domain.entity.User;

/**
 * Implementation of the UserInfoService interfaces.
 */
@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepo userRepo;
    private final UserConnectedAccountRepo userConnectedAccountRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtil;
    private final JwtUtilService jwtUtilService;


    /**
     * Signs in a user with the provided credentials.
     *
     * @param userSignInDto the user sign-in data transfer object
     * @return ResponseResult containing the JWT token if authentication is successful, or an error response
     */
    @Override
    public ResponseResult login(UserLoginDto userSignInDto) {
//        // Find the user info by username
//        UserDelete userInfo = findUserInfoByEmail(userSignInDto.getEmail());
//        // Authenticate the user
//        Authentication authentication = authenticationManager
//          .authenticate(
//            new UsernamePasswordAuthenticationToken(
//              userSignInDto.getEmail(),
//              userSignInDto.getPassword()
//            )
//          );
//        /*
//         If the user is authenticated, generate a JWT token,
//         otherwise return an error response.
//         */
//        if(authentication.isAuthenticated()) {
//            String token = jwtUtil.generateToken(new org.springframework.security.core.userdetails.User(userInfo.getEmail(), "", authentication.getAuthorities()));
//            return ResponseResult.success(token);
//        } else {
//            return ResponseResult.error(AppHttpCodeEnum.User_LOGIN_PASSWORD_ERROR);
//        }
        return null;
    }


    @Override
    public User signUpWithOauth2(Oauth2SignUpRequest oauth2SignUpRequest) {
        // Create a new user object
        User user = new User(oauth2SignUpRequest);
        userRepo.save(user);
        // Connect the oauth2 account to the user
        connectWithOauth2(new Oauth2AccountConnectRequest(oauth2SignUpRequest.getProvider(), oauth2SignUpRequest.getProviderId(), user));
        return user;
    }

    @Override
    public UserConnectedAccount findOauth2ConnectedAccount(Oauth2AccountQueryRequest accountQueryRequest) {
        return userConnectedAccountRepo
          .findByProviderAndProviderId(accountQueryRequest.getProvider(), accountQueryRequest.getProviderId())
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