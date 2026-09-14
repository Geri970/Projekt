package hu.csongor.demo.controller;

import hu.csongor.demo.entity.Jelentkezes;
import hu.csongor.demo.entity.Szakkor;
import hu.csongor.demo.repository.JelentkezesRepository;
import hu.csongor.demo.repository.SzakkorRepository;
import hu.csongor.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/jelentkezes")
public class JelentkezesController {

    private final JelentkezesRepository jelentkezesRepository;
    private final UserRepository userRepository;
    private final SzakkorRepository szakkorRepository;

    public JelentkezesController(
            JelentkezesRepository jelentkezesRepository,
            UserRepository userRepository,
            SzakkorRepository szakkorRepository) {
        this.jelentkezesRepository = jelentkezesRepository;
        this.userRepository = userRepository;
        this.szakkorRepository = szakkorRepository;
    }

    @PostMapping
    public Object jelentkezes(
            @RequestBody Jelentkezes jelentkezes){

        if(!userRepository.existsById(
                jelentkezes.getUserId())) {

            return "Nincs ilyen felhasználó!";
        }

        if(!szakkorRepository.existsById(
                jelentkezes.getSzakkorId())) {

            return "Nincs ilyen szakkör!";
        }
        if(jelentkezesRepository
                .existsByUserIdAndSzakkorId(
                        jelentkezes.getUserId(),
                        jelentkezes.getSzakkorId())){

            return "Már jelentkeztél erre a szakkörre!";
        }
        Szakkor szakkor = szakkorRepository.findById(jelentkezes.getSzakkorId()).orElse(null);
        long letszam = jelentkezesRepository.countBySzakkorId(jelentkezes.getSzakkorId());
        if( letszam >= szakkor.getMaxLetszam()){
            return "A szakkör betelt";
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
    @GetMapping("/szakkor/{id}/db")
    public Map<String, Long> getJelentkezokSzama(
            @PathVariable Long id){

        Map<String, Long> map = new HashMap<>();

        map.put(
                "jelentkezok",
                jelentkezesRepository.countBySzakkorId(id)
        );

        return map;
    }
    @GetMapping("/user/{id}")
    public List<Jelentkezes> getUserJelentkezesek(@PathVariable Long id){

        return jelentkezesRepository.findByUserId(id);
    }
    @GetMapping("/szakkor/{id}")
    public List<Jelentkezes> getSzakkorJelentkezoi(
            @PathVariable Long id){

        return jelentkezesRepository
                .findBySzakkorId(id);
    }
    @DeleteMapping("/{id}")
    public String torles(
            @PathVariable Long id){

        Jelentkezes jelentkezes =
                jelentkezesRepository.findById(id)
                        .orElse(null);

        if(jelentkezes == null){
            return "Nincs ilyen jelentkezés!";
        }

        jelentkezesRepository.delete(
                jelentkezes
        );

        return "Jelentkezés törölve!";
    }

}