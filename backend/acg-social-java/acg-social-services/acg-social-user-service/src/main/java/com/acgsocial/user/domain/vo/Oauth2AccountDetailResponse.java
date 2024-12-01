package com.acgsocial.user.domain.vo;

import com.acgsocial.user.domain.enums.Oauth2ProviderEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Oauth2AccountDetailResponse {
    @NotNull
    private Long providerId;
    @NotBlank
    private Oauth2ProviderEnum provider;
    private String email;
    private String avartarUrl;

}
