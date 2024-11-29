package com.acgsocial.user.configuration;

import com.acgsocial.user.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;


    /**
     * Bean definition for PasswordEncoder.
     * Uses BCryptPasswordEncoder with a strength of 10.
     *
     * @return PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /**
     * Configures the security filter chain.
     * Disables CSRF protection, permits all requests to /login, and requires authentication for all other requests.
     * Adds JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter.
     *
     * @param http the HttpSecurity to modify
     * @return the SecurityFilterChain
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/auth/**", "/actuator/**", "/oauth2/**", "/login/**", "/favicon**").permitAll();
                    authorize.anyRequest().authenticated();
            }).httpBasic(Customizer.withDefaults());

        http.oauth2Login(customizer -> {
            customizer.successHandler(oauth2LoginSuccessHandler);
        });


        http.exceptionHandling(customizer -> {
            customizer.authenticationEntryPoint(
              (request, response, authException) -> {
                  log.error("Unauthorized request to path: " + request.getContextPath() + authException);
                  response.sendError(401, "Unauthorized");
              });
        });



//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Bean definition for AuthenticationManager.
     * Retrieves the AuthenticationManager from the provided AuthenticationConfiguration.
     *
     * @param configuration the AuthenticationConfiguration to use
     * @return the AuthenticationManager
     * @throws Exception if an error occurs while retrieving the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}