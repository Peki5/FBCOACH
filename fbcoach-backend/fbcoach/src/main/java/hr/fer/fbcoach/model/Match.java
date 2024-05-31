package hr.fer.fbcoach.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hr.fer.fbcoach.model.util.LocationType;
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
    private LocationType location;

    @Column(name = "result")
    private String result;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_team")
    private Team teamId;

    @JsonManagedReference
    @OneToMany(mappedBy = "matchId")
    private List<TacticsApplication> tacticsApplications;

    @JsonManagedReference
    @OneToMany(mappedBy = "matchId")
    private List<PlayerMatchStats> playerMatchStats;

}
