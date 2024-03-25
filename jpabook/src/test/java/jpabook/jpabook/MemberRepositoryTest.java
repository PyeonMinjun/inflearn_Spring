//package jpabook.jpabook;
//
//import jpabook.jpabook.jpashop.JpabookApplication;
//import jpabook.jpabook.jpashop.domain.Member;
//import jpabook.jpabook.jpashop.repository.MemberRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = JpabookApplication.class)
////@Transactional // DB 롤백 테스트가 들어가있으면 안되기 때문
//class MemberRepositoryTest {
//    @Autowired
//    MemberRepository memberRepository;
//
//
//    @Test
//    @Transactional // DB 롤백 테스트가 들어가있으면 안되기 때문
////    @Rollback(value = false)
//
//    public void testMember() throws Exception {
//        //given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        //when
//        Long saveId = memberRepository.save(member);
//        Member findmember = memberRepository.find(saveId);
//
//        //then
//        assertThat(findmember.getId()).isEqualTo(member.getId());
//        assertThat(findmember.getUsername()).isEqualTo(member.getUsername());
//        assertThat(findmember).isEqualTo(member);
////      findMember == member 같나 ? 같다. 동일한 영속성 컨텍스트에서 ID가 같으면 같은 엔티티로 식별
//        // 1차 캐쉬에서 가져왔기때문에 insert만함.
//    }
//
//
//}