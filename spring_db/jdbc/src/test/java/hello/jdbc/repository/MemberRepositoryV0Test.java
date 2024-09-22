package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;


@Slf4j
public class MemberRepositoryV0Test {

    MemberRepositoryV0 memberRepositoryV0 = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {

        //save
        Member member = new Member("m2233", 10);
        memberRepositoryV0.save(member);


        //findById
        Member findMember = memberRepositoryV0.findById("m2233");
        log.info("findMember = {}", findMember);
        assertThat(findMember).isEqualTo(member);


        //update money : 10 -> 1000
        memberRepositoryV0.update(member.getMemberId(), 1000);
        Member updateMember = memberRepositoryV0.findById(member.getMemberId());
        assertThat(updateMember.getMoney()).isEqualTo(1000);


        // delete
        memberRepositoryV0.delete(member.getMemberId());
        assertThatThrownBy(() -> memberRepositoryV0.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }






}