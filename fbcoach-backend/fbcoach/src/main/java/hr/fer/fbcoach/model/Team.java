package hr.fer.fbcoach.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_team")
    private Long idTeam;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "season")
    private String season;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_korisnika")
    private Korisnik coach;

    @JsonManagedReference
    @OneToMany(mappedBy = "teamId")
    private List<Training> trainings;

    @JsonManagedReference
    @OneToMany(mappedBy = "teamId")
    private List<TeamRoster> teamRosters;

    @JsonManagedReference
    @OneToMany(mappedBy = "teamId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TacticsApplication> tacticsApplications;

}
