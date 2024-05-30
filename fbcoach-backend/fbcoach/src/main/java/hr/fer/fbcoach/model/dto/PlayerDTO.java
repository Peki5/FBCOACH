package hr.fer.fbcoach.model.dto;

import hr.fer.fbcoach.model.util.Position;
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
public class PlayerDTO {
    private Long idPlayer;
    private String firstname;
    private String lastName;
    private Date dateOfBirth;
    private Position position;
    private int height;
    private int weight;
    private List<Long> teamIds;
    private List<Long> playerMatchStatsIds;
    private List<Long> healthStatusIds;
    private List<Long> attendanceIds;
}
