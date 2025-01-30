package studyt.example.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import studyt.example.data_jpa.dto.MemberDto;
import studyt.example.data_jpa.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    //    @Query(name = "Member.findByUsername") 생략 가능
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> finduser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
// 단순히 하나만 조회
    List<String> findUsernameList();

    @Query("select new studyt.example.data_jpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findByUsername2(String name); // 컬렉션

    Member findByUsername3(String name); // 단건

    Optional<Member> findByUsername4(String name); // 단건 Optional
}
