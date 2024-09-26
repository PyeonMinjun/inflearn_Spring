package hello.jdbc.service;


import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import hello.jdbc.repository.MemberRepositoryV4_1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 예외 누수 문제 해결
 * SQLException 제거
 *
 * MemberRepository 인터페이스 의존
 */

@RequiredArgsConstructor
@SpringBootTest
@Slf4j
public class MemberServiceV4Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberServiceV4 memberServiceV4;


    @AfterEach
    void After() {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
    }
    @Test
    void AopCheck(){
        log.info("memberService = {}", memberServiceV4.getClass());
        log.info("memberRepository= {}", memberRepository.getClass());
        assertThat(AopUtils.isAopProxy(memberServiceV4)).isTrue();
        assertThat(AopUtils.isAopProxy(memberRepository)).isFalse();

    }



    @TestConfiguration
    static class TestConfig{
        private final DataSource dataSource;

        public TestConfig(DataSource dataSource){
            this.dataSource = dataSource;
        }

        @Bean
        MemberRepository memberRepository(){
            return new MemberRepositoryV4_1(dataSource);

        }
        @Bean
        MemberServiceV4 memberServiceV4(){
            return new MemberServiceV4(memberRepository());
        }
    }


    @Test
    @DisplayName("정상이체")
    void accountTransfer() {
//        memberRepositoryV1.save(Member MEMBER_A);

        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        memberServiceV4.accountTransfer(memberA.getMemberId(),memberB.getMemberId(), 2000);

        //then
        Member findByMemberA = memberRepository.findById(memberA.getMemberId());
        Member findByMemberB = memberRepository.findById(memberB.getMemberId());

        assertThat(findByMemberA.getMoney()).isEqualTo(8000);
        assertThat(findByMemberB.getMoney()).isEqualTo(12000);

    }
    @Test
    @DisplayName("이체 중 예외 발생 ")
    void accountTransferEx() {

        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        //when
        assertThatThrownBy(() -> memberServiceV4.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);


        //then
        Member findByMemberA = memberRepository.findById(memberA.getMemberId());
        Member findByMemberB = memberRepository.findById(memberEx.getMemberId());

        assertThat(findByMemberA.getMoney()).isEqualTo(10000);
        assertThat(findByMemberB.getMoney()).isEqualTo(10000);

    }
}


