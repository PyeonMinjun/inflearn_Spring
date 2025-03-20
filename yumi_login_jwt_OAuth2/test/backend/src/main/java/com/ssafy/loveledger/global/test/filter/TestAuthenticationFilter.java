package com.ssafy.loveledger.global.test.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TestAuthenticationFilter extends OncePerRequestFilter {

    @Value("${test.api.key:test-api-key}")
    private String testApiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain)
        throws ServletException, IOException {
        String apiKey = request.getHeader("X-Test-API-Key");

        if (testApiKey.equals(apiKey)) {
            // 테스트용 인증 설정
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("test-user", null,
                    List.of(new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
