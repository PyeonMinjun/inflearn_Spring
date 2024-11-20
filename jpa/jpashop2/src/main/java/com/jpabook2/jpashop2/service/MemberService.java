package com.jpabook2.jpashop2.service;

import com.jpabook2.jpashop2.domain.Member;
import com.jpabook2.jpashop2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

//    @Autowired
    private final MemberRepository memberRepository;
    /*
        회원가입
     */
    // save
    @Transactional(readOnly = false)
    public Long join(Member member) throws Exception {
        validDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 실무에서는 이렇게 작성하면 was 멀티쓰레드에서 동시접근을 할 수 있음 그래서 name을 unique 제약조건을 쓰는것이 중요.
     */

    private void validDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
        회원 전체조회 더티체크 x db야 이건 조회기능이니까 리소스 많이 읽지마
     */
    public List<Member> findMemberAll() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
