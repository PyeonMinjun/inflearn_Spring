package com.jwt.login.yumi.repository;

import com.jwt.login.yumi.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUsername(String username);


}
