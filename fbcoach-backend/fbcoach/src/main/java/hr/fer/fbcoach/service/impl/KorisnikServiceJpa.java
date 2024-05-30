package hr.fer.fbcoach.service.impl;

import hr.fer.fbcoach.model.Korisnik;
import hr.fer.fbcoach.repository.KorisnikRepository;
import hr.fer.fbcoach.service.KorisnikService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KorisnikServiceJpa implements KorisnikService {

    private KorisnikRepository korisnikRepository;


    @Override
    public List<Korisnik> listAll() {
        return korisnikRepository.findAll();
    }

    @Override
    public Korisnik createKorisnik(Korisnik korisnik) {
        Assert.notNull(korisnik, "Mora se predati korisnik!");
        Assert.isNull(korisnik.getIdKorisnika(), "Id korisnika mora biti null!");
        return korisnikRepository.save(korisnik);
    }

    @Override
    public Optional<Korisnik> fetch(String email) {
        return korisnikRepository.findByEmail(email);
    }
}