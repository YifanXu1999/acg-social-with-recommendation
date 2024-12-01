package com.acgsocial.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.client.RestOperations;

import java.time.Duration;

@Configuration
public class JWTConfig {

    public JwtDecoder jwtDecoder(RestTemplateBuilder builder) {
        RestOperations rest = builder
          .setConnectTimeout(Duration.ofSeconds(100))
          .setReadTimeout(Duration.ofSeconds(100))
          .build();

        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri("http://127.0.0.1:10000/realms/acg-social/protocol/openid-connect/certs").restOperations(rest).build();
        return jwtDecoder;
    }
}
