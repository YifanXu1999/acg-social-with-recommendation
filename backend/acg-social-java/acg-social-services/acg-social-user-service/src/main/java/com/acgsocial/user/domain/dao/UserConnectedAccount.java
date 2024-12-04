package com.acgsocial.user.domain.dao;

import com.acgsocial.user.domain.dto.Oauth2AccountConnectRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_connected_account", uniqueConstraints = {
  @UniqueConstraint(columnNames = {"provider", "providerId", "user_id"})
})
public class UserConnectedAccount extends BaseEntity {
    private String provider;
    private Long providerId;
    private LocalDateTime connectedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserConnectedAccount(Oauth2AccountConnectRequest request) {
        this.provider = request.getProvider();
        this.providerId = request.getProviderId();
        this.user = request.getUser();
    }
}
