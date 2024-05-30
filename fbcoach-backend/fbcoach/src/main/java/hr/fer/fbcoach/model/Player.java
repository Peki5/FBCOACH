package hr.fer.fbcoach.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import hr.fer.fbcoach.model.util.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reports")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_player")
    private Long idPlayer;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "position")
    private Position position;

    @Column(name = "height")
    private int height;

    @Column(name = "weight")
    private int weight;

    @JsonManagedReference
    @OneToMany(mappedBy = "playerId")
    private List<TeamRoster> teamRosters;

    @JsonManagedReference
    @OneToMany(mappedBy = "playerId")
    private List<HealthStatus> healthStatuses;

    @JsonManagedReference
    @OneToMany(mappedBy = "playerId")
    private List<Attendance> attendances;
}
