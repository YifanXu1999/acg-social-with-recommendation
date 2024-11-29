package com.acgsocial.user.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enables @PreAuthorize and @PostAuthorize
public class MethodSecurityConfig {
}
