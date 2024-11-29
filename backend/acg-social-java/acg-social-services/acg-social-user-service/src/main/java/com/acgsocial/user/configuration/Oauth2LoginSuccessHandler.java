package com.acgsocial.user.configuration;

import com.acgsocial.user.domain.dto.AccountConnectRequest;
import com.acgsocial.user.domain.dto.Oauth2SignUpRequest;
import com.acgsocial.user.domain.entity.User;
import com.acgsocial.user.domain.entity.UserConnectedAccount;
import com.acgsocial.user.repository.UserConnectedAccountRepo;
import com.acgsocial.user.repository.UserRepo;
import com.acgsocial.user.util.ApplicationContextProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ApplicationContextProvider applicationContextProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        String provider = authenticationToken.getAuthorizedClientRegistrationId();
        String providerId = authentication.getName();

        // Check if the oauth2 account is already connected to a registered account.
        Optional<UserConnectedAccount> connectedAccount = connectedAccountRepo.findByProviderAndProviderId(provider, providerId);
        if (connectedAccount.isPresent()) {
            authenticateUser(connectedAccount.get().getUser(), response);
            return;
        }

        // If the oauth2 account is not connected to a registered account, register to the system.
        Oauth2SignUpRequest oauth2SignUpRequest = new Oauth2SignUpRequest();
        User user = new User(oauth2SignUpRequest);
        userRepo.save(user);
        UserConnectedAccount userConnectedAccount = new UserConnectedAccount(new AccountConnectRequest(provider, providerId, user));
        connectedAccountRepo.save(userConnectedAccount);
        authenticateUser(user, response);

    }



    private void authenticateUser(User user, HttpServletResponse response) throws IOException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        log.info("User {} has been authenticated", token);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

}
