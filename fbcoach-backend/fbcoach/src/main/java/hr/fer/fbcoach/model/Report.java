package hr.fer.fbcoach.model;

import java.util.Date;

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
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_report")
    private Long idReport;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_korisnika")
    private Korisnik author;
}
