package com.fairytale.FairyTale.domain.uservoice.service;

import com.fairytale.FairyTale.domain.uservoice.presentation.dto.response.VoiceListResponse;

public interface UserVoiceService {

    VoiceListResponse getAvailableVoices();
}
