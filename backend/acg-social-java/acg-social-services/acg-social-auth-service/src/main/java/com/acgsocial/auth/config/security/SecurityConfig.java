package com.acgsocial.auth.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfig {

    private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;

    @Autowired
    public SecurityConfig(Oauth2LoginSuccessHandler oauth2LoginSuccessHandler) {
        this.oauth2LoginSuccessHandler = oauth2LoginSuccessHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
          .authorizeHttpRequests((authorize) -> {
              authorize.requestMatchers("/authenticate/**", "/actuator/**", "/auth/callback", "/app/login", "/login" +
                "/oauth2/code/github").permitAll();
              authorize.anyRequest().authenticated();
          });

        http.httpBasic(Customizer.withDefaults());

        http.oauth2Login(customizer -> {
              customizer.successHandler(oauth2LoginSuccessHandler);
          });

        http.addFilterBefore(new UsernamePasswordAuthenticationFilter(), LogoutFilter.class);


//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }



}
