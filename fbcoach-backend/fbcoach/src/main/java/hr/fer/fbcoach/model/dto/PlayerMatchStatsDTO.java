package hr.fer.fbcoach.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerMatchStatsDTO {
    private Long idTeamRoster;
    private int goals;
    private int assists;
    private Long playerId;
    private Long matchId;
}
