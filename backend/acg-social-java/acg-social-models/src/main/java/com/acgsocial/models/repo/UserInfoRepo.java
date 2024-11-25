package com.acgsocial.models.repo;

import com.acgsocial.models.pojo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
