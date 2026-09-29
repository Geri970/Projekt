package hu.csongor.demo.controller;

import hu.csongor.demo.dto.RegisterRequest;
import hu.csongor.demo.entity.User;
import hu.csongor.demo.repository.UserRepository;
import hu.csongor.demo.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(
            @RequestBody User user) {

        return authService.login(user);
    }
}