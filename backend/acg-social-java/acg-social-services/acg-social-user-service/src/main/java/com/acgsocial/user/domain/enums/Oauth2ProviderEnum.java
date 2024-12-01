package com.acgsocial.user.domain.enums;

public enum Oauth2ProviderEnum {
    GOOGLE("google"),
    FACEBOOK("facebook"),
    GITHUB("github");

    private String provider;

    Oauth2ProviderEnum(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }
}
