package com.ssafy.loveledger.domain.account.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class WeekStatisticsResponse {

    private Integer week;
    private Long totalConsumeSum;
    private Long totalEarnSum;
}
