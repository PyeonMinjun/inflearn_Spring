package com.yumi.OAuthJWT.service;


import com.yumi.OAuthJWT.dto.GoogleResponse;
import com.yumi.OAuthJWT.dto.NaverResponse;
import com.yumi.OAuthJWT.dto.Oath2Response;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    // oauth2User객체정보를 담음
    OAuth2User oauth2User = super.loadUser(userRequest);
    System.out.println(oauth2User);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();

    Oath2Response oath2Response = null;

    if (registrationId.equals("naver")) {
      // naver라면 네이버의 response를 사용해서 security conf에 넘겨주기
      oath2Response = new NaverResponse(oauth2User.getAttributes());


    } else if (registrationId.equals("google")) {
      oath2Response = new GoogleResponse(oauth2User.getAttributes());

    } else {
      return null;
    }

    // 추후 작성
    return null;
  }
}