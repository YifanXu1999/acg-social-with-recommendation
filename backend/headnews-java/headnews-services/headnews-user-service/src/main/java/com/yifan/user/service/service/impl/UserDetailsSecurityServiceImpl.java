package com.yifan.user.service.service.impl;

import com.yifan.common.enums.AppHttpCodeEnum;
import com.yifan.common.exception.CustomException;
import com.yifan.models.pojo.user.UserInfo;
import com.yifan.models.repo.UserInfoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

/**
 * Implementation of the UserDetailsService interfaces.
 * Provides methods for user authentication and registration.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsSecurityServiceImpl implements UserDetailsService {
    private final UserInfoRepo userInfoRepo;

    /**
     * Loads the user details by username.
     *
     * @param username the username of the user
     * @return UserDetails instance containing user information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = findUserInfoByUsername(username);
        return new User(userInfo.getUsername(), userInfo.getPassword(), new ArrayList<>());
    }

    private UserInfo findUserInfoByUsername(String username) {
        return userInfoRepo
          .findByUsername(username)
          .orElseThrow(() ->
            new CustomException(AppHttpCodeEnum.User_NOT_EXIST)
          );
    }


}
