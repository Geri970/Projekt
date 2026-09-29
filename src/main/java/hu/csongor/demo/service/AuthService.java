package hu.csongor.demo.service;

import hu.csongor.demo.dto.RegisterRequest;
import hu.csongor.demo.entity.User;
import hu.csongor.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(
            RegisterRequest request) {

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

        if (userRepository.existsByEmail(
                request.getEmail())) {

            return "Ez az email már foglalt!";
        }

        if (request.getPassword() == null ||
                request.getPassword().isBlank()) {

            return "Jelszó megadása kötelező!";
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(
                request.isTeacher()
                        ? "TANAR"
                        : "DIAK"
        );

        user.setCreatedAt(
                LocalDateTime.now().withNano(0)
        );

        user.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        user.setIsDeleted(false);
        user.setIsBanned(false);

        userRepository.save(user);

        return "Sikeres";
    }
    public String login(User user) {

        User dbUser =
                userRepository.findByName(
                        user.getName()
                );

        if (dbUser == null) {

            return "Nincs ilyen felhasználó!";
        }

        if (Boolean.TRUE.equals(
                dbUser.getIsDeleted())) {

            return "A fiók törölve van!";
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