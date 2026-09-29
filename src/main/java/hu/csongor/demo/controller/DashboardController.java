package hu.csongor.demo.controller;

import hu.csongor.demo.repository.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final SzakkorRepository szakkorRepository;
    private final JelentkezesRepository jelentkezesRepository;

    public DashboardController(
            UserRepository userRepository,
            SchoolRepository schoolRepository,
            SzakkorRepository szakkorRepository,
            JelentkezesRepository jelentkezesRepository) {

        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.szakkorRepository = szakkorRepository;
        this.jelentkezesRepository = jelentkezesRepository;
    }

    @GetMapping
    public Map<String, Object> stats() {

        Map<String, Object> map = new HashMap<>();

        map.put("users", userRepository.count());
        map.put("schools", schoolRepository.count());
        map.put("szakkorok", szakkorRepository.count());
        map.put("jelentkezesek", jelentkezesRepository.count());

        return map;
    }
}