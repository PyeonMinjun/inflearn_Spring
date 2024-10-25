package hello.servlet.web.frontcontroller.v4.controller;

import hello.servlet.member.Member;
import hello.servlet.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.List;
import java.util.Map;

public class MemberListControllerV4 implements ControllerV4 {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        List<Member> members = memberRepository.findALl();

        model.put("members", members);
        return "members";

        /**
        ModelView mv = new ModelView("members");
        mv.getModel().put("members", members);
        return  mv;
         **/

    }
}