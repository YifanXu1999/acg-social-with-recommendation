package com.acgsocial.user.util.oauth2;

import com.acgsocial.user.domain.dto.Oauth2LoginRequest;
import com.acgsocial.user.domain.vo.Oauth2AccountDetailResponse;
import com.acgsocial.user.domain.enums.Oauth2ProviderEnum;

public interface Oauth2Handler {
    public Oauth2AccountDetailResponse getAccountDetail(String code);
    public Oauth2ProviderEnum getProvider();
}
