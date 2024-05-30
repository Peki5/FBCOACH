package hr.fer.fbcoach.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_match")
    private Long idMatch;

    @Column(name = "date")
    private Date date;

    @Column(name = "opponent")
    private String opponent;

    @Column(name = "location")
    private String location;

    @Column(name = "result")
    private String result;

    @OneToMany(mappedBy = "matchId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TacticsApplication> tacticsApplications;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_team_roster")
    private TeamRoster teamRosterId;

}
