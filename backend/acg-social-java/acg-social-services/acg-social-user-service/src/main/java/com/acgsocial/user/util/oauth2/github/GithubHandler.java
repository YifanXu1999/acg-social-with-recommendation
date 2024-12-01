package com.acgsocial.user.util.oauth2.github;

import com.acgsocial.user.domain.dto.Oauth2AccountQueryRequest;
import com.acgsocial.user.domain.dto.Oauth2LoginRequest;
import com.acgsocial.user.domain.vo.Oauth2AccountDetailResponse;
import com.acgsocial.user.domain.enums.Oauth2ProviderEnum;
import com.acgsocial.user.property.OAuth2Properties;
import com.acgsocial.user.util.oauth2.Oauth2Handler;
import com.acgsocial.user.util.rest.HttpRequestClient;
import com.acgsocial.utils.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GithubHandler implements Oauth2Handler {

    private final String clientId;
    private final String clientSecret;
    private final String accessTokenUri;
    private final String userDetailUri;
    private final HttpRequestClient httpRequestClient;
    @Autowired
    public GithubHandler(OAuth2Properties oAuth2Properties, HttpRequestClient httpRequestClient) {
        OAuth2Properties.ClientProperties clientProperties = oAuth2Properties.getGithub();
        this.clientSecret = clientProperties.getClientSecret();
        this.clientId = clientProperties.getClientId();
        this.accessTokenUri = clientProperties.getAccessTokenUri();
        this.userDetailUri = clientProperties.getUserDetailUri();
        this.httpRequestClient = httpRequestClient;

    }

    @Override
    public Oauth2AccountDetailResponse getAccountDetail(String code) {
        // Get the access token
        String accessTokenResponse = getAccessTokenResponse(code);
        String accessToken = parseAcccessTokenResponse(accessTokenResponse).get("access_token");
        log.info("Access token: " + accessToken);
        // Get user details Respoonse
        Map<String, Object> userDetail = getUserDetail(accessToken);
        Oauth2AccountDetailResponse response = Oauth2AccountDetailResponse
                                                .builder()
                                                .email((String) userDetail.getOrDefault("email", ""))
                                                .provider(Oauth2ProviderEnum.GITHUB)
                                                .providerId(Long.valueOf(userDetail.get("id").toString()))
                                                .avartarUrl((String) userDetail.get("avatar_url"))
                                                .build();
        return response;
    }

    @Override
    public Oauth2ProviderEnum getProvider() {
        return Oauth2ProviderEnum.GITHUB;
    }

    private String getAccessTokenResponse(String code) {
        Map<String, String> requestParams = Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "code", code
        );
        String result = httpRequestClient.get(accessTokenUri, null, null, requestParams);
        return result;
    }

    private Map<String, Object> getUserDetail(String accessToken) {
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer " + accessToken
        );
        Map<String, Object> useDetail = JsonUtil.parse(httpRequestClient.get(userDetailUri, headers,
          null,
          null), Map.class);
        System.out.println(useDetail.toString());
        return useDetail;
    }

    private Map<String, String> parseAcccessTokenResponse(String accessTokenResponse) {
        String input = accessTokenResponse;

        // Parse the string into a map
        Map<String, String> params = Arrays.stream(input.split("&"))
          .map(param -> param.split("=", 2)) // Split each key-value pair by '='
          .collect(Collectors.toMap(
            parts -> parts[0],                // Key
            parts -> parts.length > 1 ? parts[1] : "" // Value (empty if no '=' present)
          ));

        // Retrieve the access_token

        return params;
    }



}
