package hr.fer.fbcoach.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthStatusDTO {
    private Long idHealthStatus;
    private Date date;
    private String statusNotes;
    private Long playerId;
}
