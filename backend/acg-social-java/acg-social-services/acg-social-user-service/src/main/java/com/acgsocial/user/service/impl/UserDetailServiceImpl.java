package com.acgsocial.user.service.impl;

import com.acgsocial.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl {
    private final UserRepo userRepo;
//
//    @Override
//    public User loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepo
//          .findByEmail(email)
//          .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }
//
//    @Override
//    public void createUser(UserDetails user) {
//
//    }
//
//    @Override
//    public void updateUser(UserDetails user) {
//
//    }
//
//    @Override
//    public void deleteUser(String username) {
//
//    }
//
//    @Override
//    public void changePassword(String oldPassword, String newPassword) {
//
//    }
//
//    @Override
//    public boolean userExists(String username) {
//        return false;
//    }
}
