package com.jwt.login.yumi.service;

import com.jwt.login.yumi.UserEntity;
import com.jwt.login.yumi.dto.joinDTO;
import com.jwt.login.yumi.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;


@Service
public class JoinService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    public void joinProcess(joinDTO joinDTO) {
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();


        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(bcryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}
