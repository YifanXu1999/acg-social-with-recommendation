package com.acgsocial.user.repository;

import com.acgsocial.user.domain.entity.UserConnectedAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserConnectedAccountRepo extends JpaRepository<UserConnectedAccount, Long> {
    Optional<UserConnectedAccount> findByProviderAndProviderId(String provider, String providerId);
}
