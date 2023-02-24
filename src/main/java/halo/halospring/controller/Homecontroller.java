package halo.halospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Homecontroller {

    @GetMapping("/") // localhost로 들어오게 되면 이것이 나온다.
    public String home() {
        return "home";
    }
}
