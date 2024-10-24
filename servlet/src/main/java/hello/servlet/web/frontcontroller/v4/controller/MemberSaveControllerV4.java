package hello.servlet.web.frontcontroller.v4.controller;

import hello.servlet.member.Member;
import hello.servlet.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {

    MemberRepository memberRepository = MemberRepository.getInstance();


    // 전에는 ModelAndView객체에 담아서 사용했다면 파라미터 값자체에 넘겨줘서 거기에 값을 넣고 사용하고
    // return 값 자체를 논리 path를 넘겨준다 .

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String name =paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));
        Member member = new Member(name,age);

        memberRepository.save(member);

        model.put("member", member);
        return "save-result";

    }
}
