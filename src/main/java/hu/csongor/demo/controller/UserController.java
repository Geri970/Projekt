package hu.csongor.demo.controller;


import hu.csongor.demo.entity.User;
import hu.csongor.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

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
    public Map<String, Long> getStats() {

        Map<String, Long> stats = new HashMap<>();

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
                userRepository.countByTiltva(true)
        );

        return stats;
    }




    @PutMapping("/{id}/tiltas")
    public String tiltas(@PathVariable Long id){
        User user = userRepository.findById(id)
                .orElse(null);
        if(user == null){
            return "Felhasználó nem található";
        }
        user.setTiltva(true);
        userRepository.save(user);

        return "Felhasználó tiltva";
    }
    @PutMapping("/{id}/feloldas")
    public String feloldas(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElse(null);

        if(user == null){
            return "Felhasználó nem található!";
        }

        user.setTiltva(false);

        userRepository.save(user);

        return "Felhasználó tiltva!";
    }
    @PutMapping("/{id}/tanar")
    public String tanar(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElse(null);
        if(user == null){
            return "Felhasználó nem található";
        }
        user.setRole("TANAR");
        userRepository.save(user);
        return "Felhasználó már tanár";
    }
    @DeleteMapping("/{id}/torles")
    public String torles(@PathVariable Long id) {
        System.out.println("Torles hivva, ID: " + id);
        User user = userRepository.findById(id)
                .orElse(null);
        if(user == null){
            return "Felhasználó nem található";
        }
        userRepository.delete(user);
        return "Fiók törölve";
    }


}
