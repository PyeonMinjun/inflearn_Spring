package com.yumi.OAuthJWT.ouath2;

import com.yumi.OAuthJWT.dto.CustomOAuth2User;
import com.yumi.OAuthJWT.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JWTUtil jwtUtil;

  public CustomSuccessHandler(JWTUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }
 /* authentication = 인증
    Authorities = 권한
  */


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    try {
      log.info("onAuthenticationSuccess ====================");
      //OAuth2User
      CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
      String username = customUserDetails.getUsername();

      Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
      Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
      GrantedAuthority auth = iterator.next();
      String role = auth.getAuthority();
      System.out.println("OAuth2 인증 성공, JWT 토큰 생성 시도");
      String token = jwtUtil.createJwt(username, 60 * 60 * 60L, role);
      System.out.println("JWT 토큰 생성 완료");


      response.addCookie(createCookie("Authorization", token));
      response.sendRedirect("http://localhost:3000/");
    } catch (Exception e) {
      System.err.println("OAuth2 인증 처리 중 오류 발생: " + e.getMessage());
      e.printStackTrace();
    }

  }

  private Cookie createCookie(String key, String value) {
    Cookie cookie = new Cookie(key, value);
    cookie.setMaxAge(60*60*60);
//    cookie.setSecure(true); // https
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    log.info("cookie ={}", cookie);

    return cookie;
  }
}
