package com.yifan.models.repo;

import com.yifan.models.pojo.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);
}