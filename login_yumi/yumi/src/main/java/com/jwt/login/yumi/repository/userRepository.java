package com.jwt.login.yumi.repository;

import com.jwt.login.yumi.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

public interface userRepository extends JpaRepository<UserEntity, Integer> {

}
