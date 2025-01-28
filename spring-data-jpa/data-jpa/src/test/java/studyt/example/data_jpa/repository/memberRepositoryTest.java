package studyt.example.data_jpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studyt.example.data_jpa.entity.Member;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest

class memberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void memberRepoTest() {

        Member member = new Member("편민준");
        Member savedMember = memberRepository.save(member);

        // 테스트를 위해 해당 멤버를 id로 찾고, 비교하기
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(savedMember.getId()).isEqualTo(findMember.getId());



    }

}