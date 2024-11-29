package com.acgsocial.user.service.impl;

import com.acgsocial.common.enums.AppHttpCodeEnum;
import com.acgsocial.common.exception.CustomException;
import com.acgsocial.models.pojo.user.UserDelete;
import com.acgsocial.models.repo.UserInfoRepo;
import lombok.RequiredArgsConstructor;
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

    /**
     * Loads the user details by username.
     *
     * @param email the username of the user
     * @return UserDetails instance containing user information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDelete userInfo = findUserInfoByUsername(email);
        return new org.springframework.security.core.userdetails.User(userInfo.getEmail(), userInfo.getPassword(), new ArrayList<>());
    }

    private UserDelete findUserInfoByUsername(String email) {
        return null;
    }




}
