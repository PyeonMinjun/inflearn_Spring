package com.yumi.OAuthJWT.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my")
public class MyController {

  @GetMapping
  public Map<String, String> getMyRoute() {
    System.out.println("MyController 호출됨!");

    // 단순 문자열 대신 객체 반환
    Map<String, String> response = new HashMap<>();
    response.put("message", "my route");
    response.put("timestamp", String.valueOf(System.currentTimeMillis()));

    return response;
  }
}
