package com.jwt.login.yumi.service;

import com.jwt.login.yumi.dto.CustomUserDetails;
import com.jwt.login.yumi.entity.UserEntity;
import com.jwt.login.yumi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {


    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userData = userRepository.findByUsername(username);

        if (userData != null) {
            return new CustomUserDetails(userData);
        }
        throw new UsernameNotFoundException("user not found :" + username);
    }
}
