package com.jwt.login.yumi.repository;

import com.jwt.login.yumi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUsername(String username);


    // DB에서 회원 조회
    UserEntity findByUsername(String username);


}
