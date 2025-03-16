package com.ssafy.loveledger.global.config;

import com.ssafy.loveledger.global.auth.filter.JWTFilter;
import com.ssafy.loveledger.global.auth.handler.CustomSuccessHandler;
import com.ssafy.loveledger.global.auth.service.CustomOAuth2UserService;
import com.ssafy.loveledger.global.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //cors
        http
            .cors(
                corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(
                            Collections.singletonList("http://localhost:3000")); // 3000 ?
                        configuration.setAllowedMethods(
                            Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS",
                                "X-Requested-With"));
                        configuration.setAllowedHeaders(
                            Arrays.asList("Authorization", "Content-Type", "Accept"));
                        configuration.setAllowCredentials(true);
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(
                            Arrays.asList("Set-Cookie", "Authorization"));

                        return configuration;
                    }
                }));

        http.csrf((auth) -> auth.disable());

        http.formLogin((auth) -> auth.disable());

        http.httpBasic((auth) -> auth.disable());

        http
            .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // 인증 실패 시 처리 추가
        http.exceptionHandling(exceptionHandling ->
            exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                // AJAX 요청인 경우 리다이렉트 대신 401 상태 코드 반환
                if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter()
                        .write("{\"error\":\"unauthorized\",\"message\":\"인증이 필요합니다\"}");
                } else {
                    // 일반 브라우저 요청만 OAuth 로그인으로 리다이렉트
                    response.sendRedirect("/oauth2/authorization/google");
                }
            })
        );
        //oauth2 설정
        http.oauth2Login((oauth2) -> oauth2
            .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                .userService(customOAuth2UserService))
            .successHandler(customSuccessHandler));

        //인가
        http.authorizeHttpRequests(

            (auth) -> auth.requestMatchers("/my", "/test", "/", "/oauth2/authorization/**",
                    "/login/oauth2/code/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated());

        // 세션 stateless
        http.sessionManagement(
            (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }
}
