package com.acgsocial.user.util.oauth2.github;

import com.acgsocial.user.domain.dto.Oauth2AccountQueryRequest;
import com.acgsocial.user.domain.dto.Oauth2LoginRequest;
import com.acgsocial.user.domain.vo.Oauth2AccountDetailResponse;
import com.acgsocial.user.domain.enums.Oauth2ProviderEnum;
import com.acgsocial.user.property.OAuth2Properties;
import com.acgsocial.user.util.oauth2.Oauth2Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubHandler implements Oauth2Handler {

    private final String clientId;
    private final String clientSecret;
    private final String accessTokenUri;
    private final String userDetailUri;
    private final RestTemplate restTemplate;
    @Autowired
    public GithubHandler(OAuth2Properties oAuth2Properties, RestTemplate restTemplate) {
        OAuth2Properties.ClientProperties clientProperties = oAuth2Properties.getGithub();
        this.clientSecret = clientProperties.getClientSecret();
        this.clientId = clientProperties.getClientId();
        this.accessTokenUri = clientProperties.getAccessTokenUri();
        this.userDetailUri = clientProperties.getUserDetailUri();
        this.restTemplate = restTemplate;

    }

    @Override
    public Oauth2AccountDetailResponse getAccountDetail(String code) {
        Oauth2AccountDetailResponse response = Oauth2AccountDetailResponse
                                                .builder()
                                                .email("yifanxu1999@gmail.com")
                                                .provider(Oauth2ProviderEnum.GITHUB)
                                                .providerId(77467000L)
                                                .avartarUrl("https://avatars.githubusercontent.com/u/77467000?v=4")
                                                .build();
        return response;
    }

    @Override
    public Oauth2ProviderEnum getProvider() {
        return Oauth2ProviderEnum.GITHUB;
    }

}
