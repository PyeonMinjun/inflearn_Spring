package com.yumi.OAuthJWT.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/my")
public class MyController {

  @GetMapping
  public String MyController() {
    return "my route";
  }
}
