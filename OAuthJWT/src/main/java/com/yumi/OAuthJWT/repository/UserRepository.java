package com.yumi.OAuthJWT.repository;

import com.yumi.OAuthJWT.entitiy.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, String> {

  UserEntity findByUsername(String username);

}
