package com.yifan.user.service.impl;

import com.yifan.common.enums.AppHttpCodeEnum;
import com.yifan.common.exception.CustomException;
import com.yifan.common.result.ResponseResult;
import com.yifan.models.dto.user.UserLoginDto;
import com.yifan.models.dto.user.UserSignUpDto;
import com.yifan.models.pojo.user.UserInfo;
import com.yifan.models.repo.UserInfoRepo;
import com.yifan.user.service.UserInfoService;
import com.yifan.utils.jwt.JwtUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Implementation of the UserInfoService interfaces.
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService{

    private final UserInfoRepo userInfoRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtil;
    private final PasswordEncoder passwordEncoder;


    /**
     * Signs in a user with the provided credentials.
     *
     * @param userSignInDto the user sign-in data transfer object
     * @return ResponseResult containing the JWT token if authentication is successful, or an error response
     */
    @Override
    public ResponseResult login(UserLoginDto userSignInDto) {
        // Find the user info by username
        UserInfo userInfo = findUserInfoByEmail(userSignInDto.getEmail());
        // Authenticate the user
        Authentication authentication = authenticationManager
          .authenticate(
            new UsernamePasswordAuthenticationToken(
              userSignInDto.getEmail(),
              userSignInDto.getPassword()
            )
          );
        /*
         If the user is authenticated, generate a JWT token,
         otherwise return an error response.
         */
        if(authentication.isAuthenticated()) {
            String token = jwtUtil.generateToken(new User(userInfo.getEmail(), "", authentication.getAuthorities()));
            return ResponseResult.success(token);
        } else {
            return ResponseResult.error(AppHttpCodeEnum.User_LOGIN_PASSWORD_ERROR);
        }
    }

    /**
     * Signs up a new user with the provided information.
     *
     * @param userSignUpDto the user sign-up data transfer object
     * @return ResponseResult containing the JWT token if registration is successful, or an error response
     */
    @Override
    public ResponseResult signUp(UserSignUpDto userSignUpDto) {
				// Check if the user already exists
		    userInfoRepo
			    .findByEmail(userSignUpDto.getEmail())
			    .ifPresent(userInfo -> {
		                throw new CustomException(AppHttpCodeEnum.User_EXIST);
                  });
        // Create a new user info object
        UserInfo userInfo = UserInfo
                                .builder()
                                .email(userSignUpDto.getEmail())
                                .password(passwordEncoder.encode(userSignUpDto.getPassword()))
                                .build();
        // Save the user info
        userInfoRepo.save(userInfo);
        // Generate and return the JWT token
        String token = jwtUtil.generateToken(new User(userInfo.getEmail(), "", new ArrayList<>()));
        return ResponseResult.success(token);
    }

    /**
     * Finds the user information by username.
     *
     * @param email the username of the user
     * @return UserInfo instance containing user information
     * @throws CustomException if the user is not found
     */
    private UserInfo findUserInfoByEmail(String email) {
        return userInfoRepo
		              .findByEmail(email)
		              .orElseThrow(() ->
		                new CustomException(AppHttpCodeEnum.User_NOT_EXIST)
		              );
    }
}