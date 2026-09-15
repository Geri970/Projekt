package hu.csongor.demo.controller;

import hu.csongor.demo.dto.RegisterRequest;
import hu.csongor.demo.entity.User;
import hu.csongor.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request) {

        if (request.getName() == null ||
                request.getName().isBlank()) {

            return "Név megadása kötelező!";
        }

        if (userRepository.existsByName(
                request.getName())) {

            return "Ez a név már foglalt!";
        }

        if (request.getEmail() == null ||
                request.getEmail().isBlank()) {

            return "Email megadása kötelező!";
        }

        if (!request.getEmail().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

            return "Érvénytelen email cím!";
        }

        if (request.getPassword() == null ||
                request.getPassword().isBlank()) {

            return "Jelszó megadása kötelező!";
        }

        if (request.getPassword().length() < 6) {

            return "A jelszónak legalább 6 karakter hosszúnak kell lennie!";
        }

        if (userRepository.existsByEmail(
                request.getEmail())) {

            return "Ez az email már foglalt!";
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setCreatedAt(
                LocalDateTime.now().withNano(0)
        );

        user.setIsBanned(false);
        user.setIsDeleted(false);

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        if (request.isTeacher()) {

            if (!request.getTeacherCode()
                    .equals("ISKOLA2026")) {

                return "Hibás tanári kód!";
            }

            user.setRole("TANAR");

        } else {

            user.setRole("DIAK");
        }

        userRepository.save(user);

        return "Sikeres";
    }

    @PostMapping("/login")
    public String login(
            @RequestBody User user) {

        User dbUser =
                userRepository.findByName(
                        user.getName()
                );

        if (dbUser == null) {

            return "Nincs ilyen felhasználó!";
        }

        if (Boolean.TRUE.equals(
                dbUser.getIsBanned())) {

            return "Fiók le van tiltva!";
        }

        boolean matches =
                passwordEncoder.matches(
                        user.getPassword(),
                        dbUser.getPassword()
                );

        if (!matches) {

            return "Hibás jelszó!";
        }

        return "Sikeres";
    }
}