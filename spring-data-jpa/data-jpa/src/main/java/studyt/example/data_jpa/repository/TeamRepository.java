package studyt.example.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyt.example.data_jpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
