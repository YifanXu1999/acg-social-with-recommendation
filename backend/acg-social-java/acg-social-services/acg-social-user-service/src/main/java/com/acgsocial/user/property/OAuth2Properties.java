package com.acgsocial.user.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.oauth2")
@Getter
public class OAuth2Properties {


    private final ClientProperties github = new ClientProperties();
    private final ClientProperties google = new ClientProperties();


    @Getter
    @Setter
    public static class ClientProperties {
        private String clientId;
        private String clientSecret;
        private String accessTokenUri;
        private String userDetailUri;


    }
}