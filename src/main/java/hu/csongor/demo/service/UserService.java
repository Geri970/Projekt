package hu.csongor.demo.service;

import hu.csongor.demo.entity.User;
import hu.csongor.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import hu.csongor.demo.dto.response.UserResponse;

import java.util.HashMap;
import java.util.Map;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public List<UserResponse> getUsers() {

        List<User> users =
                userRepository.findByIsDeleted(false);

        return users.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .toList();
    }

    public String tiltas(Integer id) {

        User user = userRepository
                .findById(id)
                .orElse(null);

        if(user == null){
            return "Felhasználó nem található";
        }

        user.setIsBanned(true);

        userRepository.save(user);

        return "Felhasználó tiltva";
    }

    public String feloldas(Integer id) {

        User user = userRepository
                .findById(id)
                .orElse(null);

        if(user == null){
            return "Felhasználó nem található";
        }

        user.setIsBanned(false);

        userRepository.save(user);

        return "Felhasználó feloldva";
    }

    public String tanarraTesz(Integer id){

        User user = userRepository
                .findById(id)
                .orElse(null);

        if(user == null){
            return "Felhasználó nem található";
        }

        user.setRole("TANAR");

        userRepository.save(user);

        return "Felhasználó már tanár";
    }

    public String torles(Integer id){

        User user = userRepository
                .findById(id)
                .orElse(null);

        if(user == null){
            return "Felhasználó nem található";
        }

        user.setIsDeleted(true);
        user.setDeletedAt(
                LocalDateTime.now().withNano(0)
        );

        userRepository.save(user);

        return "Fiók törölve";
    }
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
}