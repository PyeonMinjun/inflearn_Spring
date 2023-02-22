package halo.halospring.repository;

import halo.halospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // sequence는 0,1,2 ~ key값을 생성
                                        //마찬가지 실무에서는 어텀롱을 사용해야함

    //save를 할때 어딘가에 저장을 해야함
    // 실무에서는 동시성 문제가있어서 공유되는 변수의 경우 컨커르테시벨(?)사용해야함
    // -> 예제에서는 테시벨 사용

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // 시퀀스값을 올려줌
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) { // store에서 꺼내면됨
        return Optional.ofNullable(store.get(id)); // 결과가 없다면 ? -> null이 반환될 가능성이있음
                                                    // Optional.ofNullable로 감싸줌
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() //람다사용 루프를 돌리는것
                .filter(member -> member.getName().equals(name)) // member에서 membergetname이 파라미터넘어온 name이랑 같은지 비교
                                                                    // 같은경우에만 필터링이됨
                .findAny(); //하나라도 찾는것 => 결과가 Optional로 반환이됨
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
