package halo.halospring.service;

import halo.halospring.domain.Member;
import halo.halospring.repository.MemberRepository;
import halo.halospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional



class memberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository; // memberRepository 객체를 만들어줌


    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member); // join을 검증

        //then 검증
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName()); //static import 하여 줄여줌 (member 이름 ==  findMember.getName?)
    }

    @Test
    public void 중복_회원_예외(){
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");     // 중복회원

        // when
        memberService.join((member1));
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));// 이 로직을 탈때 동작
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }


}