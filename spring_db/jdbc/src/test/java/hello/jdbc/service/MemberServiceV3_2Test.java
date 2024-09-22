package hello.jdbc.service;


import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@RequiredArgsConstructor
public class MemberServiceV3_2Test {
    /**
     * 트랜잭션 - 트랜잭션 매니저
     */
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";


    private MemberRepositoryV3 memberRepositoryV3;
    private MemberServiceV3_2 memberServiceV3_2;

    @BeforeEach
    void BeforeEach() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        memberRepositoryV3 = new MemberRepositoryV3(dataSource);
        memberServiceV3_2 = new MemberServiceV3_2(transactionManager, memberRepositoryV3);

    }

    @AfterEach
    void After() throws SQLException {
        memberRepositoryV3.delete(MEMBER_A);
        memberRepositoryV3.delete(MEMBER_B);
        memberRepositoryV3.delete(MEMBER_EX);


    }



    @Test
    @DisplayName("정상이체")
    void accountTransfer() throws SQLException {
//        memberRepositoryV1.save(Member MEMBER_A);

        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepositoryV3.save(memberA);
        memberRepositoryV3.save(memberB);

        //when
        memberServiceV3_2.accountTransfer(memberA.getMemberId(),memberB.getMemberId(), 2000);

        //then
        Member findByMemberA = memberRepositoryV3.findById(memberA.getMemberId());
        Member findByMemberB = memberRepositoryV3.findById(memberB.getMemberId());

        assertThat(findByMemberA.getMoney()).isEqualTo(8000);
        assertThat(findByMemberB.getMoney()).isEqualTo(12000);

    }
    @Test
    @DisplayName("이체 중 예외 발생 ")
    void accountTransferEx() throws SQLException {

        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);
        memberRepositoryV3.save(memberA);
        memberRepositoryV3.save(memberEx);

        //when
        assertThatThrownBy(() -> memberServiceV3_2.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);


        //then
        Member findByMemberA = memberRepositoryV3.findById(memberA.getMemberId());
        Member findByMemberB = memberRepositoryV3.findById(memberEx.getMemberId());

        assertThat(findByMemberA.getMoney()).isEqualTo(10000);
        assertThat(findByMemberB.getMoney()).isEqualTo(10000);

    }
}


