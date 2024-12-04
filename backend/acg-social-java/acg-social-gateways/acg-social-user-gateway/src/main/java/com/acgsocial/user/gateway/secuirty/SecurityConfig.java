package com.acgsocial.user.gateway.secuirty;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

public class SecurityConfig {

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
    http.authorizeExchange(
        exchanges -> {
          exchanges.anyExchange().permitAll();
        }
    );


    return http.build();
  }
}