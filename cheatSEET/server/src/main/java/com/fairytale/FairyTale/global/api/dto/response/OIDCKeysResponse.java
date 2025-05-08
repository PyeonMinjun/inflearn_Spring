package com.fairytale.FairyTale.global.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OIDCKeysResponse {

    List<OIDCKeyDto> keys;
}
