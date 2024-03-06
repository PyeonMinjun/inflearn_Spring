package halo.halospring.repository;

import halo.halospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {   //메서드가 실행되고 끝날때마다 동작하게 됨 , 콜백 메소드
        repository.clearStore();
    }

    @Test
    public  void save(){
        Member member = new Member();
        member.setName("spring");


        repository.save(member);

        Member result = repository.findById(member.getId()).get(); // 내가 적은 아이디가 제대로 들어가나 확인한다.
                                                // 반환타입이 Optinal 이므로 get을 이용해 꺼냄
        //내가 new에서 꺼낸 값이랑 DB에서 꺼낸값이랑 같은지 확인
//        System.out.println("result == " + (result == member));

//        Assertions.assertEquals(member, result);
        assertThat(member).isEqualTo(result); // 더편해진방법 static import를 하여 assertThat없이도 동작할수있다.

    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get(); // get을하면 Optional을 까서 넣을수있음

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2); // 사이즈가 2개가 나와야함 3개시 오류

    }

}
