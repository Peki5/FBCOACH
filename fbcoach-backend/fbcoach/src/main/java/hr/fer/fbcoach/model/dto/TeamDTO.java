package hr.fer.fbcoach.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {

    private Long idTeam;
    private String teamName;
    private String season;
    private Long coachId;
    private List<Long> trainingIds;
    private List<Long> teamRosterIds;
    private List<Long> tacticsApplicationIds;
}
