package com.acgsocial.user.gateway.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SessionDetail {
    private String sessionId;
    private String userId;
}
