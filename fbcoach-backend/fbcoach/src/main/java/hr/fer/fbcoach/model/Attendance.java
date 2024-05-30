package hr.fer.fbcoach.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hr.fer.fbcoach.model.util.AttendanceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attendance")
    private Long idAttendance;

    @Column(name = "date")
    private Date date;

    @Column(name = "is_present")
    private boolean isPresent;

    @Column(name = "type")
    private AttendanceType type;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_player")
    private Player playerId;
}
