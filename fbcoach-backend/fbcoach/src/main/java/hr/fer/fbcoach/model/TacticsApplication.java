package hr.fer.fbcoach.model;

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
@Table(name = "tacticsApplication")
public class TacticsApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tactic_application")
    private Long idTacticsApplication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tactics")
    private Tactics tacticsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_match")
    private Match matchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_team")
    private Team teamId;
}
