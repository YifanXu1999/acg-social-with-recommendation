package com.acgsocial.user.domain.entity;

import com.acgsocial.user.domain.dto.EmailSignUpRequest;
import com.acgsocial.user.domain.dto.Oauth2SignUpRequest;
import com.acgsocial.user.domain.enums.Role;
import com.acgsocial.user.util.ApplicationContextProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User extends BaseEntity implements UserDetails {

    @Column(unique = true, nullable = true)
    private String email;

    @Column
    @JsonIgnore
    private String password;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @Column
    private boolean verified = false;

    @Column(name = "profile_image_url")
    private String profileImageUrl = "";

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        this.username = generateRandomUsername();
        this.role = Role.USER;
    }


    public User(EmailSignUpRequest signUpRequest) {
        this();
        this.email = signUpRequest.getEmail();
        this.password = ApplicationContextProvider.bean(PasswordEncoder.class).encode(signUpRequest.getPassword());
    }

    public User(Oauth2SignUpRequest signUpRequest) {
        this();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    private String generateRandomUsername() {
        return "user-" + UUID.randomUUID().toString();
    }
}
