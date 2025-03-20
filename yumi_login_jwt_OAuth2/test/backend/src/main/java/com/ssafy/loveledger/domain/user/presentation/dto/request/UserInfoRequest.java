package com.ssafy.loveledger.domain.user.presentation.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;

@Data
public class UserInfoRequest {

    @NotBlank(message = "이름은 필수 입력값입니다")
    @Size(min = 2, max = 64, message = "이름은 2-64자 사이여야 합니다")
    private String name;

    @NotNull(message = "성별은 필수 입력값입니다")
    private Boolean gender;

    @NotNull(message = "생일은 필수 입력값입니다")
    @Past(message = "생일은 과거 날짜여야 합니다")
    private Date birthDay;

    @NotNull(message = "결혼여부는 필수 입력값입니다")
    private Boolean isMarried;
}
