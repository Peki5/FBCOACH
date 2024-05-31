package hr.fer.fbcoach.model.dto;

import hr.fer.fbcoach.model.util.AttendanceType;
import hr.fer.fbcoach.model.util.PresentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private Long idAttendance;
    private Date date;
    private PresentType present;
    private AttendanceType type;
    private Long playerId;
}
