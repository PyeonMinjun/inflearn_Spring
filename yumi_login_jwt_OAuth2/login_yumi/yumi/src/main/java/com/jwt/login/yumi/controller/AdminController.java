package com.jwt.login.yumi.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody
public class AdminController {

    @GetMapping("/admin")
    public String adminController() {
        System.out.println("admin 컨트롤러 출력");
        return "관리자 페이지";

    }
}
