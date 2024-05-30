package hr.fer.fbcoach.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchDTO {
    private Long idMatch;
    private Date date;
    private String opponent;
    private String location;
    private String result;
    private Long teamId;
    private List<Long> playerMatchStatsIds;
    private List<Long> tacticsApplicationIds;
}
