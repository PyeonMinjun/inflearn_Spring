package com.fairytale.FairyTale.domain.uservoice.presentation;

import com.fairytale.FairyTale.domain.uservoice.presentation.dto.response.VoiceListResponse;
import com.fairytale.FairyTale.domain.uservoice.service.UserVoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-voice")
public class UserVoiceController {

    private final UserVoiceService userVoiceService;

    @GetMapping
    public VoiceListResponse getAvailableVoices() {
        return userVoiceService.getAvailableVoices();
    }
}
