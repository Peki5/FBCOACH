package hr.fer.fbcoach.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teamRoster")
public class TeamRoster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_team_roster")
    private Long idTeamRoster;

    @Column(name = "goals")
    private int goals;

    @Column(name = "assists")
    private int assists;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_team")
    private Team teamId;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_player")
    private Player playerId;

    @JsonManagedReference
    @OneToMany(mappedBy = "teamRosterId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Match> matches;

}
