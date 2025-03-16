package com.ssafy.loveledger.global.auth.filter;

import com.ssafy.loveledger.global.auth.dto.request.CustomOAuth2User;
import com.ssafy.loveledger.global.auth.dto.request.UserDto;
import com.ssafy.loveledger.global.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/oauth2/") || path.startsWith("/login/oauth2/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // OPTIONS 메서드는 프리플라이트 요청이므로 인증 검사 없이 통과시킴
        if (request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

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
        UserDto userDto = UserDto.builder()
            .role(role)
            .username(username)
            .build();

        //UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDto);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null,
            customOAuth2User.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
