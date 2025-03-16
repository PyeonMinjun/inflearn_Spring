package com.ssafy.loveledger.global.auth.handler;

import com.ssafy.loveledger.global.auth.dto.request.CustomOAuth2User;
import com.ssafy.loveledger.global.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User customUserDetail = (CustomOAuth2User) authentication.getPrincipal();
        String username = customUserDetail.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        String token = jwtUtil.createJwt(username, 60 * 60 * 60L, role);

        response.addCookie(createCookie("Authorization", token));
        response.sendRedirect("http://localhost:3000/");
        System.out.println("================= cookie" + token);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60);
        //    cookie.setSecure(true); // https
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;


    }
}
