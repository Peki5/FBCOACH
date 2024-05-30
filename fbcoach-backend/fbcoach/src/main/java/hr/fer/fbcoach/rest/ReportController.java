package hr.fer.fbcoach.rest;

import hr.fer.fbcoach.model.Report;
import hr.fer.fbcoach.model.dto.ReportDTO;
import hr.fer.fbcoach.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ReportController {

    private final ReportService reportService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public List<ReportDTO> listAll() {
        List<Report> reports = reportService.listAllReportsForSignedInAuthor();
        return reports.stream()
                .sorted(Comparator.comparing(Report::getIdReport))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long id) {
        Report report = reportService.getReportById(id);
        if (report != null) {
            return ResponseEntity.ok(convertToDto(report));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/add")
    public ResponseEntity<ReportDTO> addReport(@RequestBody ReportDTO reportDTO) {
        Report report = convertToEntity(reportDTO);
        Report createdReport = reportService.addReport(report);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdReport));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ReportDTO> editReport(@PathVariable Long id, @RequestBody ReportDTO reportDTO) {
        Report report = convertToEntity(reportDTO);
        Report updatedReport = reportService.editReport(id, report);
        return ResponseEntity.ok(convertToDto(updatedReport));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    private ReportDTO convertToDto(Report report) {
        return modelMapper.map(report, ReportDTO.class);
    }

    private Report convertToEntity(ReportDTO reportDTO) {
        return modelMapper.map(reportDTO, Report.class);
    }
}
