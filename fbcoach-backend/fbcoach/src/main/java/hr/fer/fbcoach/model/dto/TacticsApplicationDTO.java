package hr.fer.fbcoach.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TacticsApplicationDTO {
    private Long idTacticsApplication;
    private String description;
    private Long tacticsId;
    private Long matchId;
}
