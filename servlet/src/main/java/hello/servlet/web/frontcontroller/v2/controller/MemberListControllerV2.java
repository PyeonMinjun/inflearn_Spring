package hello.servlet.web.frontcontroller.v2.controller;

import hello.servlet.member.Member;
import hello.servlet.member.MemberRepository;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class MemberListControllerV2 implements ControllerV2 {


    private MemberRepository memberRepository = MemberRepository.getInstance();


    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {
        List<Member> members = memberRepository.findALl();

        request.setAttribute("members", members);

        MyView myView = new MyView("/WEB-INF/views/members.jsp");
        return myView;


    }
}
