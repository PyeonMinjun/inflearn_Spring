package halo.halospring.service;

import halo.halospring.domain.Member;
import halo.halospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository; // memberRepository 객체를 만들어줌

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {   //메서드가 실행되고 끝날때마다 동작하게 됨 , 콜백 메소드
        memberRepository.clearStore();
    } // repo에 있는 것을 초기화

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

       /* try {
            memberService.join(member2);            // 멤버 2를 넣었2지만 spring으로 같은 상태
            fail();                                 // 여기까지오면 fail
        } catch (IllegalStateException e){          // 예외가발생하면 이문으로 옴
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/

        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}