package halo.halospring;


import halo.halospring.repository.JdbcMemberRepository;
import halo.halospring.repository.MemberRepository;
import halo.halospring.repository.MemoryMemberRepository;
import halo.halospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {



    private  DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }



    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() { // 인터페이스  new 가안됨
        return new JdbcMemberRepository(dataSource); // 구현체 //메모리에 올린것을 JDBC로 바꾼다.

    }

}
