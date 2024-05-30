package hr.fer.fbcoach.model.dto;

import hr.fer.fbcoach.model.Match;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamRosterDTO {
    private Long idTeamRoster;
    private int goals;
    private int assists;
    private Long teamId;
    private Long playerId;
    private List<Long> matchesIds;
}
