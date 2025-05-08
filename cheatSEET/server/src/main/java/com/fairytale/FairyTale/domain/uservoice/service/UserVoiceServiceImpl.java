package com.fairytale.FairyTale.domain.uservoice.service;

import com.fairytale.FairyTale.domain.user.domain.User;
import com.fairytale.FairyTale.domain.user.domain.repository.UserRepository;
import com.fairytale.FairyTale.domain.uservoice.domain.UserVoice;
import com.fairytale.FairyTale.domain.uservoice.domain.respository.UserVoiceRepository;
import com.fairytale.FairyTale.domain.uservoice.presentation.dto.response.VoiceListResponse;
import com.fairytale.FairyTale.global.exception.UserNotFoundException;
import com.fairytale.FairyTale.global.util.user.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserVoiceServiceImpl implements UserVoiceService {

    private final UserUtils userUtils;
    private final UserRepository userRepository;
    private final UserVoiceRepository userVoiceRepository;

    @Override
    public VoiceListResponse getAvailableVoices() {

        User currentUser = userUtils.getUserFromSecurityContext();
        Long userId = currentUser.getId();
        userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.EXCEPTION);

        return VoiceListResponse.from(userVoiceRepository.findByUserWithDefaultVoices(currentUser));
    }
}
