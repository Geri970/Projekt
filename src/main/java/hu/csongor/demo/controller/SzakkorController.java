package hu.csongor.demo.controller;

import hu.csongor.demo.entity.School;
import hu.csongor.demo.entity.Szakkor;
import hu.csongor.demo.repository.JelentkezesRepository;
import hu.csongor.demo.repository.SchoolRepository;
import hu.csongor.demo.repository.SzakkorRepository;
import hu.csongor.demo.dto.response.SzakkorResponse;
import hu.csongor.demo.dto.response.JelentkezoResponse;
import hu.csongor.demo.entity.Jelentkezes;
import hu.csongor.demo.entity.User;
import hu.csongor.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/szakkor")
public class SzakkorController {

    private final SzakkorRepository szakkorRepository;
    private final SchoolRepository schoolRepository;
    private final JelentkezesRepository jelentkezesRepository;
    private final UserRepository userRepository;


    public SzakkorController(
            SzakkorRepository szakkorRepository,
            SchoolRepository schoolRepository,
            JelentkezesRepository jelentkezesRepository,
            UserRepository userRepository) {

        this.szakkorRepository = szakkorRepository;
        this.schoolRepository = schoolRepository;
        this.jelentkezesRepository = jelentkezesRepository;
        this.userRepository = userRepository;
    }


    @GetMapping
    public List<SzakkorResponse> getAllSzakkorok() {

        List<Szakkor> szakkorok =
                szakkorRepository.findByDeleted(false);

        return szakkorok.stream()
                .map(szakkor -> new SzakkorResponse(
                        szakkor.getId(),
                        szakkor.getName(),
                        szakkor.getLeiras(),
                        szakkor.getIdopont(),
                        szakkor.getHelyszin(),
                        szakkor.getMaxLetszam(),
                        jelentkezesRepository
                                .countBySzakkorId(
                                        szakkor.getId()
                                )
                ))
                .toList();
    }
    @PostMapping
    public Object createSzakkor(
            @RequestBody Szakkor szakkor) {
        if(szakkor.getName() == null ||
                szakkor.getName().isBlank()){

            return "A szakkör neve kötelező!";
        }

        if(szakkorRepository.existsByName(
                szakkor.getName())){

            return "Már létezik ilyen szakkör";
        }
        if(szakkor.getName() == null || szakkor.getName().isBlank()){
            return "A szakkör neve kötelező!";
        }
        if(szakkor.getHelyszin() == null || szakkor.getHelyszin().isBlank()){
            return "A szakkör helyszíne kötelező!";
        }
        if(szakkor.getLeiras() == null || szakkor.getLeiras().isBlank()) {
            return "A szakkör leírása kötelező!";
        }
        if(szakkor.getIdopont() == null || szakkor.getIdopont().isBlank()){
            return "A szakkör időpontját megadni kötelező!";
        }
        if(szakkor.getMaxLetszam() <= 0){
            return "A maximális létszám nem lehet 0!";
        }
        szakkor.setCreatedAt(
                LocalDateTime.now().withNano(0)
        );

        szakkor.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );
        szakkor.setDeleted(false);
        School school = schoolRepository
                .findById(szakkor.getSchoolId())
                .orElse(null);

        if(school == null){
            return "Nincs ilyen iskola!";
        }

        if(!school.getStatusz().equals("APPROVED")){
            return "Az iskola nincs jóváhagyva!";
        }
        return szakkorRepository.save(szakkor);
    }
    @GetMapping("/school/{id}")
    public List<Szakkor> getSchoolSzakkorok(@PathVariable Integer id) {
        return szakkorRepository.findBySchoolId(id);
    }
    @GetMapping("/{id}/applicants")
    public List<JelentkezoResponse> getJelentkezok(
            @PathVariable Integer id) {

        List<Jelentkezes> jelentkezesek =
                jelentkezesRepository.findBySzakkorId(id);

        return jelentkezesek.stream()
                .map(jelentkezes -> {

                    User user = userRepository
                            .findById(
                                    jelentkezes.getUserId()
                            )
                            .orElse(null);

                    return new JelentkezoResponse(
                            user.getId(),
                            user.getName(),
                            user.getEmail()
                    );
                })
                .toList();
    }
    @DeleteMapping("/{id}/del")
    public String torles(@PathVariable Integer id){
        Szakkor szakkor = szakkorRepository.findById(id).orElse(null);
        if(szakkor == null){
            return "Nincs ilyen szakkör";
        }
        szakkor.setDeleted(true);

        szakkor.setDeletedAt(
                LocalDateTime.now().withNano(0)
        );

        szakkorRepository.save(szakkor);

        return "Szakkör törölve";
    }
    @PutMapping("/{id}/update")
    public String updateSzakkor(
            @PathVariable Integer id,
            @RequestBody Szakkor ujAdatok) {

        Szakkor szakkor = szakkorRepository
                .findById(id)
                .orElse(null);

        if(szakkor == null){
            return "Nincs ilyen szakkör!";
        }

        szakkor.setName(ujAdatok.getName());
        szakkor.setLeiras(ujAdatok.getLeiras());
        szakkor.setIdopont(ujAdatok.getIdopont());
        szakkor.setHelyszin(ujAdatok.getHelyszin());
        szakkor.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );
        szakkor.setMaxLetszam(ujAdatok.getMaxLetszam());

        szakkorRepository.save(szakkor);

        return "Szakkör módosítva!";
    }
}