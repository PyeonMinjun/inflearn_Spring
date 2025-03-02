package com.yumi.OAuthJWT.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

//  private static final String HARD_CODED_SECRET = "vmfhaltmskdlstkfkdgodyroqkfwkdbalroqkfwkdbalaaaaaaaaaaaaaaaabbbbb";
  private SecretKey secretKey;

//  public JWTUtil() {
//    this.secretKey = Keys.hmacShaKeyFor(HARD_CODED_SECRET.getBytes(StandardCharsets.UTF_8));
//  }
  private JWTUtil(@Value("${spring.jwt.secret}") String secret) {
    System.out.println("==== JWTUtil 생성자 실행됨 ====");
    System.out.println("Secret 길이: " + secret.length());
//    secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SIG.HS256.key().build().getAlgorithm());
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    System.out.println("Secret key algorithm: " + this.secretKey.getAlgorithm());
  }

  public String getUsername(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
  }

  public String getRole(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
  }

  public Boolean isExpired(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
  }

  public String createJwt(String username, Long expiredMs, String role) {
    try {
      System.out.println("==== createJwt 메서드 호출됨 ====");
      System.out.println("username: " + username);
      System.out.println("role: " + role);
      return Jwts.builder()
          .claim("username", username)
          .claim("role", role)
          .issuedAt(new Date(System.currentTimeMillis()))
          .expiration(new Date(System.currentTimeMillis() + expiredMs))
          .signWith(secretKey)
          .compact();
    } catch (Exception e) {
      e.printStackTrace();
      throw  e;
    }



  }
}
