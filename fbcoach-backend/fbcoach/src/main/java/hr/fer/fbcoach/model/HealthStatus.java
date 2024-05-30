package hr.fer.fbcoach.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "healthStatus")
public class HealthStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_health_status")
    private Long idHealthStatus;

    @Column(name = "date")
    private Date date;

    @Column(name = "status_notes")
    private String statusNotes;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_player")
    private Player playerId;
}
