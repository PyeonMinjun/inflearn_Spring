package hello.login.domain.login;


import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {


    private final MemberRepository memberRepository;

    /**
     * @return null 로그인  실패
     */

    public Member login(String longId, String password) {
   /*     Optional<Member> findMemberOptional = memberRepository.findByLongId(longId);
        Member member = findMemberOptional.get();
        if (member.getPassword().equals(password)) {
            return member;
        }else{
            return null;
        }
        */
        return memberRepository.findByLongId(longId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

}
