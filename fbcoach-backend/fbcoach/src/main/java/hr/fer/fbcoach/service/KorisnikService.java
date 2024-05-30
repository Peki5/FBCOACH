package hr.fer.fbcoach.service;



import hr.fer.fbcoach.model.Korisnik;

import java.util.List;
import java.util.Optional;

public interface KorisnikService {

    List<Korisnik> listAll();

    Korisnik createKorisnik(Korisnik korisnik);

    Optional<Korisnik> fetch(String email);

}