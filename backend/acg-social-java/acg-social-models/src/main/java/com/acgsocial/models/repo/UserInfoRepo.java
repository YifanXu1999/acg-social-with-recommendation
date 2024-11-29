package com.acgsocial.models.repo;

import com.acgsocial.models.pojo.user.UserDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserDelete, Long> {
    Optional<UserDelete> findByEmail(String email);
}
