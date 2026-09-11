package hu.csongor.demo.controller;

import hu.csongor.demo.entity.Jelentkezes;
import hu.csongor.demo.repository.JelentkezesRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/jelentkezes")
public class JelentkezesController {

    private final JelentkezesRepository jelentkezesRepository;

    public JelentkezesController(
            JelentkezesRepository jelentkezesRepository) {

        this.jelentkezesRepository = jelentkezesRepository;
    }

    @PostMapping
    public Object jelentkezes(
            @RequestBody Jelentkezes jelentkezes){

        if(jelentkezesRepository
                .existsByUserIdAndSzakkorId(
                        jelentkezes.getUserId(),
                        jelentkezes.getSzakkorId())){

            return "Már jelentkeztél erre a szakkörre!";
        }

        jelentkezes.setJelentkezesDatum(
                LocalDateTime.now().withNano(0)
        );

        return jelentkezesRepository.save(
                jelentkezes
        );
    }
    @GetMapping
    public List<Jelentkezes> getAllJelentkezes() {
        return jelentkezesRepository.findAll();
    }


}