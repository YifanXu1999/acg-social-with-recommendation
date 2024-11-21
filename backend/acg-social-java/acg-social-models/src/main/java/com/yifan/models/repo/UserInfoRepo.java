package com.yifan.models.repo;

import com.yifan.models.pojo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
