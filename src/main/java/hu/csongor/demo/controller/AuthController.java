package hu.csongor.demo.controller;

import hu.csongor.demo.entity.User;
import hu.csongor.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import hu.csongor.demo.dto.RegisterRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request) {

        if(userRepository.existsByNev(
                request.getNev())) {

            return "Ez a nev mar foglalt!";
        }

        User user = new User();

        user.setNev(request.getNev());
        user.setEmail(request.getEmail());
        user.setRegisztracioDatum(
                LocalDateTime.now().withNano(0)
        );
        user.setTiltva(false);
        user.setJelszo(
                passwordEncoder.encode(
                        request.getJelszo()
                )
        );

        if(userRepository.existsByEmail(user.getEmail())){
            return "Ez az email már foglalt!";
        }
        if(request.isTeacher()) {

            if(!request.getTeacherCode()
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
    public String login(@RequestBody User user) {

        User dbUser = userRepository.findByNev(user.getNev());

        if(dbUser == null) {
            return "Nincs ilyen felhasznalo!";
        }
        if(dbUser.isTiltva()){
            return "Fiók le van tiltva!";
        }
        System.out.println("Beirt jelszo: " + user.getJelszo());
        System.out.println("DB hash: " + dbUser.getJelszo());
        boolean matches = passwordEncoder.matches(
                user.getJelszo(),
                dbUser.getJelszo()
        );

        if(!matches) {
            return "Hibas jelszo!";
        }

        return "Sikeres";
    }


}