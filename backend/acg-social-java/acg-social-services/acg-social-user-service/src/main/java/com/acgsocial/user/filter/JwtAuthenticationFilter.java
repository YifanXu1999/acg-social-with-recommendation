package com.acgsocial.user.filter;

import com.acgsocial.user.domain.pojo.JWTUserDetails;
import com.acgsocial.utils.jwt.JwtUtilService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtilService jwtService;

    private final UserDetailsService userDetailsService;


    /**
     * This method is called for every request to check if the request contains a valid JWT token.
     * If a valid JWT token is found, the user is authenticated.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if an error occurs during the filtering process
     * @throws IOException      if an I/O error occurs during the filtering process
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Get the Authorization header from the request
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("Cookies: " + Arrays.stream(cookies).map(Cookie::getName).collect(Collectors.joining(", ")));
        Optional<Cookie> jwtCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("Access-Token")).findFirst();
        if(jwtCookie.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = jwtCookie.get().getValue();


        try {
            // Extract the username from the JWT token
            final String userId = jwtService.extracUserId(jwt);
            Claims claims = jwtService.extractAllClaims(jwt);
            // Get the current authentication from the SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // If the username is not null and the user is not authenticated, proceed with authentication
            if (userId != null && authentication == null) {
                // Load the user details using the username
                JWTUserDetails userDetails = new JWTUserDetails(claims);

                // If the JWT token is valid, create an authentication token and set it in the SecurityContext
                if (true) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            // Continue the filter chain
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            // Handle any exceptions that occur during the filtering process
            throw new ServletException(exception);
        }

    }
}
