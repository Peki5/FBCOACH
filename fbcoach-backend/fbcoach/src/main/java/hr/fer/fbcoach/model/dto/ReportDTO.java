package hr.fer.fbcoach.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private Long idReport;
    private Date date;
    private String description;
    //private Long authorId; // or you can use an AuthorDTO if you want more details about the author
}
