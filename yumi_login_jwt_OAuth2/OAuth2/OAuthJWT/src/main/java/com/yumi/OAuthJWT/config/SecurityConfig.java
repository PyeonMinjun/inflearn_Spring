package com.yumi.OAuthJWT.config;

import com.yumi.OAuthJWT.jwt.JWTFilter;
import com.yumi.OAuthJWT.jwt.JWTUtil;
import com.yumi.OAuthJWT.ouath2.CustomSuccessHandler;
import com.yumi.OAuthJWT.service.CustomOauth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final CustomOauth2UserService customOauth2UserService;
  private final CustomSuccessHandler customSuccessHandler;
  private final JWTUtil jwtUtil;

  public SecurityConfig(CustomOauth2UserService customOauth2UserService, CustomSuccessHandler customSuccessHandler, JWTUtil jwtUtil) {
    this.customOauth2UserService = customOauth2UserService;
    this.customSuccessHandler = customSuccessHandler;
    this.jwtUtil = jwtUtil;
  }
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    //cors
    http
        .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

          @Override
          public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

            CorsConfiguration configuration = new CorsConfiguration();

            configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setMaxAge(3600L);

            configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
            configuration.setExposedHeaders(Collections.singletonList("Authorization"));

            return configuration;
          }
        }));

    // csrf disable
    http
        .csrf((auth) -> auth.disable());

    // form 로그인방식 disable
    http
        .formLogin((auth) -> auth.disable());

    // HTTP Basic 방식 disable
    http
        .httpBasic((auth) -> auth.disable());

    //JWTFilter 추가
    http
        .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);


    // oauth2 설정
    http
        .oauth2Login((oauth2) -> oauth2
            .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                .userService(customOauth2UserService))
            .successHandler(customSuccessHandler)
        );

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
