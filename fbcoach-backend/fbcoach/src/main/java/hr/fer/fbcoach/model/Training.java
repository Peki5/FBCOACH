package hr.fer.fbcoach.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hr.fer.fbcoach.model.util.TrainingType;
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
@Table(name = "training")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_training")
    private Long idTraining;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private TrainingType type;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_team")
    private Team teamId;
}
