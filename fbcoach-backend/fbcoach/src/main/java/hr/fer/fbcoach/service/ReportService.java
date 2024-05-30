package hr.fer.fbcoach.service;

import hr.fer.fbcoach.model.Report;

import java.util.List;

public interface ReportService {
    List<Report> listAllReportsForSignedInAuthor();

    Report addReport(Report report);

    Report editReport(Long id, Report report);

    void deleteReport(Long id);

    Report getReportById(Long id);
}
