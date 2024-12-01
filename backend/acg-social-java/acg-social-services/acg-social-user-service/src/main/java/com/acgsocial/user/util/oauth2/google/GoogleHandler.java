package com.acgsocial.user.util.oauth2.google;

import com.acgsocial.user.domain.dto.Oauth2LoginRequest;
import com.acgsocial.user.domain.vo.Oauth2AccountDetailResponse;
import com.acgsocial.user.domain.enums.Oauth2ProviderEnum;
import com.acgsocial.user.util.oauth2.Oauth2Handler;
import org.springframework.stereotype.Component;

@Component
public class GoogleHandler implements Oauth2Handler {


    @Override
    public Oauth2AccountDetailResponse getAccountDetail(String code) {
        System.out.println("GoogleStrategy.getAccountDetail");
        return null;
    }

    @Override
    public Oauth2ProviderEnum getProvider() {
        return Oauth2ProviderEnum.GOOGLE;
    }
}
