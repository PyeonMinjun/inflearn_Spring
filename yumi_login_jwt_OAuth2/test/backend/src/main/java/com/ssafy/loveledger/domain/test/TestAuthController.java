//package com.ssafy.loveledger.domain.test;
//
//import com.ssafy.loveledger.global.auth.util.JWTUtil;
//import java.util.HashMap;
//import java.util.Map;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@Profile({"dev", "test"})
//@RequestMapping("/test")
//@RequiredArgsConstructor
//public class TestAuthController {
//
//    private final JWTUtil jwtUtil;
//
//
//    @GetMapping("/token")
//    public ResponseEntity<Map<String, String>> getTestToken(
//        @RequestParam(required = false, defaultValue = "test-user") String username) {
//        String access = jwtUtil.createJwt("access", username, 86400000L);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("accessToken", access);
//
//        return ResponseEntity.ok(response);
//    }
//}