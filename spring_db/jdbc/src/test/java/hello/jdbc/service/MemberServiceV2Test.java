package hello.jdbc.service;


import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@RequiredArgsConstructor
public class MemberServiceV2Test {
    /**
     * 트랜잭션 - 커넥션 파라미터 전달 방식 동기화
     */
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";


    private MemberRepositoryV2 memberRepositoryV2;
    private MemberServiceV2 memberServiceV2;

    @BeforeEach
    void BeforeEach() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepositoryV2 = new MemberRepositoryV2(dataSource);
        memberServiceV2 = new MemberServiceV2(dataSource, memberRepositoryV2);

    }

    @AfterEach
    void After() throws SQLException {
        memberRepositoryV2.delete(MEMBER_A);
        memberRepositoryV2.delete(MEMBER_B);
        memberRepositoryV2.delete(MEMBER_EX);


    }



    @Test
    @DisplayName("정상이체")
    void accountTransfer() throws SQLException {
//        memberRepositoryV1.save(Member MEMBER_A);

        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepositoryV2.save(memberA);
        memberRepositoryV2.save(memberB);

        //when
        memberServiceV2.accountTransfer(memberA.getMemberId(),memberB.getMemberId(), 2000);

        //then
        Member findByMemberA = memberRepositoryV2.findById(memberA.getMemberId());
        Member findByMemberB = memberRepositoryV2.findById(memberB.getMemberId());

        assertThat(findByMemberA.getMoney()).isEqualTo(8000);
        assertThat(findByMemberB.getMoney()).isEqualTo(12000);

    }
    @Test
    @DisplayName("이체 중 예외 발생 ")
    void accountTransferEx() throws SQLException {

        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);
        memberRepositoryV2.save(memberA);
        memberRepositoryV2.save(memberEx);

        //when
        assertThatThrownBy(() -> memberServiceV2.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);


        //then
        Member findByMemberA = memberRepositoryV2.findById(memberA.getMemberId());
        Member findByMemberB = memberRepositoryV2.findById(memberEx.getMemberId());

        assertThat(findByMemberA.getMoney()).isEqualTo(10000);
        assertThat(findByMemberB.getMoney()).isEqualTo(10000);

    }
}


