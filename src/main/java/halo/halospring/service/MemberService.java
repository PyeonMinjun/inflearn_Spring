package halo.halospring.service;

import halo.halospring.domain.Member;
import halo.halospring.repository.MemberRepository;
import halo.halospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class MemberService {



    private  final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    /*
        회원가입
     */
    public Long join(Member member){
        //같은 이름이 있는 중복 회원X
        validateDuplicateMember(member); // 중복 회원검증

        memberRepository.save(member); // 회원가입은간단하다 save에다가 member을 넣어주기만하면 된다
        return member.getId();

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(member1 -> { // 값이 있으면
                             throw new IllegalStateException("이미 존재하는 회원입니다.");
                               });
    }

    /*
        전체회원조회

     */
    public List<Member> findMembers() { // 서비스는 join findMembers처럼 기존 값을 넣었다 뺏다가아닌 비즈니스에 가깝다.
        return memberRepository.findAll();
    }

    public  Optional<Member> findOne(Long memberId){
        return  memberRepository.findById(memberId);
    }

}
