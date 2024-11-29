package com.acgsocial.user.configuration;

import com.acgsocial.user.domain.dto.Oauth2AccountConnectRequest;
import com.acgsocial.user.domain.dto.Oauth2AccountQueryRequest;
import com.acgsocial.user.domain.dto.Oauth2SignUpRequest;
import com.acgsocial.user.domain.entity.User;
import com.acgsocial.user.domain.entity.UserConnectedAccount;
import com.acgsocial.user.domain.vo.AuthTokenResponse;
import com.acgsocial.user.repository.UserConnectedAccountRepo;
import com.acgsocial.user.repository.UserRepo;
import com.acgsocial.user.service.UserAuthService;
import com.acgsocial.user.util.ApplicationContextProvider;
import com.acgsocial.utils.json.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserConnectedAccountRepo connectedAccountRepo;
    private final UserRepo userRepo;

    @Lazy
    @Autowired
    private  UserAuthService userAuthService;
    private final ApplicationContextProvider applicationContextProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        String provider = authenticationToken.getAuthorizedClientRegistrationId();
        String providerId = authentication.getName();

        // Check if the oauth2 account is already connected to a registered account.
        UserConnectedAccount connectedAccount = userAuthService.findOauth2ConnectedAccount(new Oauth2AccountQueryRequest(provider, providerId));
        if (connectedAccount != null) {
            log.info("User already connected with provider {} and providerId {}", provider, providerId);
            authenticateUser(connectedAccount.getUser(), response);
            return;
        }

        // If the oauth2 account is not connected to a registered account, register to the system.
        User user = userAuthService.signUpWithOauth2(new Oauth2SignUpRequest(provider, providerId));
        authenticateUser(user, response);

    }



    private void authenticateUser(User user, HttpServletResponse response) throws IOException {
        AuthTokenResponse authTokenResponse = userAuthService.generatenNewAuthToken(user);
        response.setContentType("application/json");
        response.getWriter().write(JsonUtil.stringify(authTokenResponse));

    }

}
