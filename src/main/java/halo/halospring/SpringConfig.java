package halo.halospring;


import halo.halospring.repository.MemberRepository;
import halo.halospring.repository.MemoryMemberRepository;
import halo.halospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() { // 인터페이스  new 가안됨
        return new MemoryMemberRepository(); // 구현체


    }

}
