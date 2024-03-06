package halo.halospring.repository;

import halo.halospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 회원을저장 -> 저장된 회원이 반환
    Optional<Member> findById(Long id); //방금 id로 찾음
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
