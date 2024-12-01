package com.acgsocial.user.util.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

//TODO change it to restclient for non-blocking requests
@Service
@Slf4j
public class HttpRequestClient {

    private final RestTemplate restTemplate;

    public HttpRequestClient() {
        this.restTemplate = new RestTemplate();
    }


    public String get(String url, Map<String, String> headers, Map<String, String> pathVariables,
                      Map<String, String> requestParams) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(headers != null) {
            headers.forEach(httpHeaders::add);
        }
        HttpEntity<Object> headerEntity = new HttpEntity<>(httpHeaders);

        if(pathVariables != null) {
            AtomicReference<String> atomicUrl = new AtomicReference<>(url);
            pathVariables
              .forEach((key, value) ->
                atomicUrl.updateAndGet(currentUrl ->
                  currentUrl.replace("{" + key + "}", value))
              );
            url = atomicUrl.get();
        }

        if(requestParams != null) {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            requestParams.forEach(params::add);
            url = url + buildQueryString(params);
        }

        log.info("Requesting GET: " +url);

        log.info(headerEntity.toString());
        ResponseEntity<String> response = restTemplate.exchange(
          url,
          HttpMethod.GET,
          headerEntity,
          String.class
        );
        log.info("Response: " + response.getBody());
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