package studyt.example.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyt.example.data_jpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
