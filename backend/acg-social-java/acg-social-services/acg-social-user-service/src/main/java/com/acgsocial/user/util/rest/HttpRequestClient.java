package com.acgsocial.user.util.rest;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

//TODO change it to restclient for non-blocking requests
@Service
public class HttpRequestClient {

    private final RestTemplate restTemplate;

    public HttpRequestClient() {
        this.restTemplate = new RestTemplate();
    }

    public String get(String url, Map<String, String> pathVariables, Map<String, String> requestParams) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Replace path variables in the URL
        for (Map.Entry<String, String> entry : pathVariables.entrySet()) {
            url = url.replace("{" + entry.getKey() + "}", entry.getValue());
        }

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        requestParams.forEach(params::add);

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
          url + buildQueryString(params),
          HttpMethod.GET,
          entity,
          String.class
        );
        return response.getBody();
    }

    public String postRequest(String url, Object requestBody, Map<String, String> requestParams) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        requestParams.forEach(params::add);

        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
          url + buildQueryString(params),
          HttpMethod.POST,
          entity,
          String.class
        );
        return response.getBody();
    }

    private String buildQueryString(MultiValueMap<String, String> params) {
        StringBuilder queryString = new StringBuilder("?");
        params.forEach((key, valueList) -> {
            valueList.forEach(value -> queryString.append(key).append("=").append(value).append("&"));
        });
        return queryString.substring(0, queryString.length() - 1);
    }
}