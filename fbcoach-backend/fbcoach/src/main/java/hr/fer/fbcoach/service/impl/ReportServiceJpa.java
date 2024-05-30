package hr.fer.fbcoach.service.impl;

import hr.fer.fbcoach.model.Korisnik;
import hr.fer.fbcoach.model.Report;
import hr.fer.fbcoach.repository.ReportRepository;
import hr.fer.fbcoach.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceJpa implements ReportService {

    private ReportRepository reportRepository;

    @Override
    public List<Report> listAllReportsForSignedInAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Korisnik loggedKorisnik) {
            return reportRepository.findByAuthor(loggedKorisnik);
        }
        return null;
    }

    @Override
    public Report addReport(Report report) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Korisnik loggedKorisnik) {
            report.setAuthor(loggedKorisnik);
            return reportRepository.save(report);
        }
        return null;
    }

    @Override
    public Report editReport(Long id, Report report) {
        Report existingReport = reportRepository.findById(id).orElse(null);
        if (existingReport != null) {
            existingReport.setDate(report.getDate());
            existingReport.setDescription(report.getDescription());
            return reportRepository.save(existingReport);
        }
        return null;
    }

    @Override
    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    @Override
    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }
}
