package com.yumi.OAuthJWT.config;

import com.yumi.OAuthJWT.service.CustomOauth2UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfig {

  private final CustomOauth2UserService customOauth2UserService;

  public securityConfig(CustomOauth2UserService customOauth2UserService) {
    this.customOauth2UserService = customOauth2UserService;
  }

  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


    // csrf disable
    http
        .csrf((auth) -> auth.disable());

    // form 로그인방식 disable
    http
        .formLogin((auth) -> auth.disable());

    // HTTP Basic 방식 disable
    http
        .httpBasic((auth) -> auth.disable());

    // oauth2 설정
    http
        .oauth2Login((oauth2) -> oauth2
            .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                .userService(customOauth2UserService)));

    // 경로별 인가작업
    http
        .authorizeHttpRequests((auth) -> auth
            .requestMatchers("/").permitAll()
            .anyRequest().authenticated());

    // 세션 stateless
    http
        .sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }
}
