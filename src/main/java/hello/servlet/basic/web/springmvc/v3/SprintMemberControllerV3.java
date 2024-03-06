package hello.servlet.basic.web.springmvc.v3;

import hello.servlet.basic.domain.member.Member;
import hello.servlet.basic.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("springmvc/v3/members")
public class SprintMemberControllerV3 {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @GetMapping("/new-form")
    public String newFrom() {
        return "new-form";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam("username") String name,
            @RequestParam("age") int age,
            Model model){

        Member member = new Member(name, age);
        memberRepository.save(member);
        model.addAttribute("member", member);
        return "save-result";

    }
    @GetMapping
    public String Members(Model model) {
        List<Member> members= memberRepository.findAll();
        model.addAttribute("members",members);
        return "members";

    }
}
