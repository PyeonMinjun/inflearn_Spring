package com.jwt.login.yumi.controller;

import com.jwt.login.yumi.dto.joinDTO;
import com.jwt.login.yumi.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@ResponseBody
public class JoinController {

    private JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(joinDTO joinDTO) {


        System.out.println(joinDTO.getUsername());
        joinService.joinProcess(joinDTO);
        return "ok";



    }

}
