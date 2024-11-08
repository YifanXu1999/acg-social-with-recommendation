package com.yifan.service.userserice.service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("admin", "$2a$10$oDnTErG/GdkiRmXfU97wzuFIP2W6ekrz4H4Y3gtEqaQaqSd3jAWfK", new ArrayList<>());
    }
}
