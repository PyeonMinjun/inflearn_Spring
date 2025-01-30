package studyt.example.data_jpa.dto;

import lombok.Data;
import studyt.example.data_jpa.entity.Team;

@Data
public class MemberDto {
    private Long id;
    private String username;
    private String team;


    public MemberDto(Long id, String username, String team) {
        this.id = id;
        this.username = username;
        this.team = team;
    }
}
