package hr.fer.fbcoach.model.dto;

import hr.fer.fbcoach.model.TacticsApplication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TacticsDTO {
    private Long idTactics;
    private String name;
    private String description;
    List<Long> tacticsApplicationsIds;
}