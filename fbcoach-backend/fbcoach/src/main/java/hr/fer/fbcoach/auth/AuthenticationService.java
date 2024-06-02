package hr.fer.fbcoach.auth;

import hr.fer.fbcoach.config.JwtService;
import hr.fer.fbcoach.model.Korisnik;
import hr.fer.fbcoach.model.util.Role;
import hr.fer.fbcoach.repository.KorisnikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final KorisnikRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws EmailAlreadyExistsException {
        Optional<Korisnik> oldUser = repository.findByEmail(request.getEmail());
        if (oldUser.isPresent()) {
            throw new EmailAlreadyExistsException("User with this email already exists");
        }

        var korisnik = Korisnik.builder()
                .ime(request.getIme())
                .prezime(request.getPrezime())
                .email(request.getEmail())
                .lozinka(passwordEncoder.encode(request.getLozinka()))
                .role(Role.USER)
                .build();
        repository.save(korisnik);
        var jwtToken = jwtService.generateToken(korisnik);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getLozinka()
                )
        );
        var korisnik = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(korisnik);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .ime(korisnik.getIme())
                .prezime(korisnik.getPrezime())
                .email(korisnik.getEmail())
                .idKorisnika(korisnik.getIdKorisnika())
                .build();
    }
}