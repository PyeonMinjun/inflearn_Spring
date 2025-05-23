package com.fairytale.FairyTale.global.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OIDCKeyDto {

    private String kid;
    private String alg;
    private String use;
    private String n;
    private String e;
}
