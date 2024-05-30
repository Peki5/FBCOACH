package hr.fer.fbcoach.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "player_match_stats")
public class PlayerMatchStats {

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

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_match")
    private Match matchId;

}
