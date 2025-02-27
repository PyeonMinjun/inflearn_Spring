package com.yumi.OAuthJWT.service;


import com.yumi.OAuthJWT.dto.CustomOAuth2User;
import com.yumi.OAuthJWT.dto.GoogleResponse;
import com.yumi.OAuthJWT.dto.NaverResponse;
import com.yumi.OAuthJWT.dto.Oath2Response;
import com.yumi.OAuthJWT.dto.UserDto;
import org.apache.catalina.User;
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
    // 리소스 서버에 실제 API 호출을 수행 -> 리소스 서버는 요청받은 사용자 정보(이름, 이메일 등)를 반환합니다.
    // oauth2User 객체에 이 정보가 담깁니다.
    OAuth2User oauth2User = super.loadUser(userRequest);
    System.out.println(oauth2User);

    // registrationId는 어떤 소셜 로그인 서비스(google, naver 등)를 사용했는지 식별합니다.
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

    // 리소스 서버에서 발급받은 정보로 사용자를 특정할 아이디값을 만듬.
    UserDto userDto = new UserDto();

    String username = oath2Response.getProvider() + " " + oath2Response.getProviderId();
    userDto.setName(oath2Response.getName());
    userDto.setUsername(username);
    userDto.setRole("ROLE_USER");

    return new CustomOAuth2User(userDto);
  }
}