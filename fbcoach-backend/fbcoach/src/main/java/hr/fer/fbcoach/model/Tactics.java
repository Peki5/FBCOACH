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
@Table(name = "tactics")
public class Tactics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tactics")
    private Long idTactics;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_team")
    private Team teamId;

    @JsonManagedReference
    @OneToMany(mappedBy = "tacticsId")
    private List<TacticsApplication> tacticsApplications;
}
