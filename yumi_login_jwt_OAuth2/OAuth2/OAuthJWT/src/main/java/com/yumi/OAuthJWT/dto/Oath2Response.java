package com.yumi.OAuthJWT.dto;

public interface Oath2Response {

  // 제공자
  String getProvider();

  // 제공자에서 발급해주는 ID
  String getProviderId();

  // 이메일
  String getEmail();

  // 사용자 실명
  String getName();

}
