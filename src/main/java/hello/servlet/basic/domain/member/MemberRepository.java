package hello.servlet.basic.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {

    private static final Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;


    private static final MemberRepository instance = new MemberRepository();

    private MemberRepository() {
    }

    public static MemberRepository getInstance() {
        return instance;
    }

    //todo save
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    //todo findById
    public Member findById(Long id) {
        return store.get(id);
    }

    //todo findAll
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    //todo clearAll
    public void clearStore() {
        store.clear();
    }


}
