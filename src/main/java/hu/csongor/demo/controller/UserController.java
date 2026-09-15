package hu.csongor.demo.controller;

import hu.csongor.demo.entity.User;
import hu.csongor.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        Map<String, Object> stats = new HashMap<>();

        stats.put(
                "osszes",
                userRepository.count()
        );

        stats.put(
                "diakok",
                userRepository.countByRole("DIAK")
        );

        stats.put(
                "tanarok",
                userRepository.countByRole("TANAR")
        );

        stats.put(
                "tiltott",
                userRepository.countByIsBanned(true)
        );

        return stats;
    }

    @PutMapping("/{id}/tiltas")
    public String tiltas(
            @PathVariable Integer id) {

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return "Felhasználó nem található";
        }

        user.setIsBanned(true);

        userRepository.save(user);

        return "Felhasználó tiltva";
    }

    @PutMapping("/{id}/feloldas")
    public String feloldas(
            @PathVariable Integer id) {

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return "Felhasználó nem található!";
        }

        user.setIsBanned(false);

        userRepository.save(user);

        return "Felhasználó feloldva!";
    }

    @PutMapping("/{id}/tanar")
    public String tanar(
            @PathVariable Integer id) {

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return "Felhasználó nem található";
        }

        user.setRole("TANAR");

        userRepository.save(user);

        return "Felhasználó már tanár";
    }

    @DeleteMapping("/{id}/torles")
    public String torles(
            @PathVariable Integer id) {

        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return "Felhasználó nem található";
        }

        userRepository.delete(user);

        return "Fiók törölve";
    }
}