package com.yumi.OAuthJWT.jwt;

import com.yumi.OAuthJWT.dto.CustomOAuth2User;
import com.yumi.OAuthJWT.dto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTFilter extends OncePerRequestFilter {

  private final JWTUtil jwtUtil;

  public JWTFilter(JWTUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    // jwt 검증
    // jwtUtil에서 네임이랑 expire 들고오기 ?
    // 그리고 헤더값에서 Authorization 들고와서 null 비교
    // 다음 필터로 넘겨주기

    //cookie들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음
    String authorization = null;
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("Authorization")) {
        authorization = cookie.getValue();
      }
    }

    //Authorization 헤더 검증
    if (authorization == null) {
      System.out.println("token null");
      filterChain.doFilter(request, response);

      //조건이 해당되면 메소드 종료(필수)
      return;
    }
    // 토큰
    String token = authorization;

    //토큰 소멸 시간 검증
    if (jwtUtil.isExpired(token)) {
      System.out.println("token expired");
      filterChain.doFilter(request, response);
      //조건이 해당되면 메소드 종료(필수)
      return;
    }

    //토큰에서 username , role 값 흭득
    String role = jwtUtil.getRole(token);
    String username = jwtUtil.getUsername(token);

    //userDTO를 생성하여 값 set
    UserDto userDto = new UserDto();
    userDto.setRole(role);
    userDto.setUsername(username);

    //UserDetails에 회원 정보 객체 담기
    CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDto);

    // 스프링 시큐리티 인증 토큰 생성
    Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

    //세션에 사용자 등록
    SecurityContextHolder.getContext().setAuthentication(authToken);



  }
}
