package hr.fer.fbcoach.model.dto;

import hr.fer.fbcoach.model.util.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDTO {

    private Long idTraining;
    private Date date;
    private String description;
    private TrainingType type;
    private Long teamId;
}
