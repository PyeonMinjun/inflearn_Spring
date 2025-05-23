package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.member.Member;
import hello.servlet.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        List<Member> members = memberRepository.findALl();
        ModelView mv = new ModelView("members");
        mv.getModel().put("members", members);

        return mv;


    }
}
