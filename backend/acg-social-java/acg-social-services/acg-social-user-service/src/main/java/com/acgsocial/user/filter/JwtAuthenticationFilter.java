package com.acgsocial.user.filter;

import com.acgsocial.utils.jwt.JwtUtilService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
import java.io.IOException;
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
        final String authHeader = request.getHeader("Authorization");

        // If the Authorization header is missing or does not start with "Bearer ", continue the filter chain
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract the JWT token from the Authorization header
            final String jwt = authHeader.substring(7);
            // Extract the username from the JWT token
            final String username = jwtService.extractUsername(jwt);

            // Get the current authentication from the SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // If the username is not null and the user is not authenticated, proceed with authentication
            if (username != null && authentication == null) {
                // Load the user details using the username
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // If the JWT token is valid, create an authentication token and set it in the SecurityContext
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // Set the authentication details from the request
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
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
