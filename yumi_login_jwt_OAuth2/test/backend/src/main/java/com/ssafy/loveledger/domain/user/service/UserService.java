package com.ssafy.loveledger.domain.user.service;

import com.ssafy.loveledger.domain.user.domain.User;
import com.ssafy.loveledger.domain.user.domain.repository.UserRepository;
import com.ssafy.loveledger.domain.user.presentation.dto.request.UserInfoRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUserInfo(Long userId, UserInfoRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + userId));

        User updatedUser = User.builder()
            .id(user.getId())
            .email(user.getEmail())
            .provider(user.getProvider())
            .usercode(user.getUsercode())
            .name(request.getName())
            .gender(request.getGender())
            .birthDay(request.getBirthDay())
            .isMarried(request.getIsMarried())
            .build();

        userRepository.save(updatedUser);
    }
/*
    public UserResponse updateUser(String userId, UserUpdateRequest request) {

        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        // 정보 업데이트 (null이 아닌 필드만)
        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }

//        if (request.getBirthday() != null) {
//            user.setBirthday(parseBirthdayToLocalDateTime(request.getBirthday()));
//        }

        if (request.getIsMarried() != null) {
            user.setIsMarried(request.getIsMarried());
        }

        // 저장
        User savedUser = userRepository.save(user);

        // 응답 DTO 변환
        return UserResponse.builder()
            .name(savedUser.getName())
            .gender(savedUser.getGender())
//            .birthday(formatLocalDateTimeToString(savedUser.getBirthday()))
            .isMarried(savedUser.getIsMarried())
            .build();
    }

 */
}


