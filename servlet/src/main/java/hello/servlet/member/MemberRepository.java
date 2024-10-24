package hello.servlet.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequnce  = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }
    private MemberRepository() {
    }


    public Member save(Member member) {
        member.setId(sequnce++);
        store.put(member.getId(), member);
        return member;
    }


    public Member findById(Long id) {
        Member member = store.get(id);
        return member;
    }

    public List<Member> findALl() {
        return new ArrayList<>(store.values());

    }

    public void clearStore(){
        store.clear();
    }



}
