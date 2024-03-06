package hello.servlet.basic2.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {

    private static final Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public MemberRepository() {
    }

    private final static MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance(){
        return instance;
    }





    //save
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    //findById
    public Member findById(Long id) {
        return store.get(id);
    }

    //findAll
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }


    //storeClear
    public void storeClear() {
        store.clear();
    }


}
