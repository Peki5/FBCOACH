package hr.fer.fbcoach.repository;

import hr.fer.fbcoach.model.Korisnik;
import hr.fer.fbcoach.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByAuthor(Korisnik author);
}