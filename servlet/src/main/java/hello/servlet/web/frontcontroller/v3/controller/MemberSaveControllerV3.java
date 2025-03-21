package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.member.Member;
import hello.servlet.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        String name =paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));
        Member member = new Member(name,age);

        memberRepository.save(member);

        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member", member);
        return mv;





    }
}
