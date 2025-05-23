package hello.jdbc.service;


import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;


@RequiredArgsConstructor
public class MemberServiceV1Test {
    /**
     * 기본동작 , 트랜잭션이 없어서 문제 발생
     */
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";


    private MemberRepositoryV1 memberRepositoryV1;
    private MemberServiceV1 memberServiceV1;

    @BeforeEach
    void BeforeEach() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepositoryV1 = new MemberRepositoryV1(dataSource);
        memberServiceV1 = new MemberServiceV1(memberRepositoryV1);

    }

    @AfterEach
    void After() throws SQLException {
        memberRepositoryV1.delete(MEMBER_A);
        memberRepositoryV1.delete(MEMBER_B);
        memberRepositoryV1.delete(MEMBER_EX);


    }



    @Test
    @DisplayName("정상이체")
    void accountTransfer() throws SQLException {
//        memberRepositoryV1.save(Member MEMBER_A);

        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepositoryV1.save(memberA);
        memberRepositoryV1.save(memberB);

        //when
        memberServiceV1.accountTransfer(memberA.getMemberId(),memberB.getMemberId(), 2000);

        //then
        Member findByMemberA = memberRepositoryV1.findById(memberA.getMemberId());
        Member findByMemberB = memberRepositoryV1.findById(memberB.getMemberId());

        assertThat(findByMemberA.getMoney()).isEqualTo(8000);
        assertThat(findByMemberB.getMoney()).isEqualTo(12000);

    }
    @Test
    @DisplayName("이체 중 예외 발생 ")
    void accountTransferEx() throws SQLException {

        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);
        memberRepositoryV1.save(memberA);
        memberRepositoryV1.save(memberEx);

        //when
        assertThatThrownBy(() -> memberServiceV1.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);


        //then
        Member findByMemberA = memberRepositoryV1.findById(memberA.getMemberId());
        Member findByMemberB = memberRepositoryV1.findById(memberEx.getMemberId());

        assertThat(findByMemberA.getMoney()).isEqualTo(8000);
        assertThat(findByMemberB.getMoney()).isEqualTo(10000);

    }
}


