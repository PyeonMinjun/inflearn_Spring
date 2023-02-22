package halo.halospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public  String hello(Model model) {
        model.addAttribute("data","hello!!");
        return "hello";

    }
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";

    }
    @GetMapping("hello-string")
    @ResponseBody
    public  String helloString(@RequestParam("name") String name){
        return "hello-string" + name; //"hello spring"
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        // HelloController.Hello 방식을 사용할수있다 클래스안 클래스 (자바정식 문법)
        private String name;

        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }
    }

}
