package com.ssafy.loveledger.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String name;
    private Boolean gender;
    private String birthday; // YYYYMMDD 형식 문자열
    private Boolean isMarried;
}
