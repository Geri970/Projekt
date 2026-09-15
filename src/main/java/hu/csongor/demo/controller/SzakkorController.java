package hu.csongor.demo.controller;

import hu.csongor.demo.entity.Szakkor;
import hu.csongor.demo.repository.SzakkorRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/szakkor")
public class SzakkorController {

    private final SzakkorRepository szakkorRepository;

    public SzakkorController(
            SzakkorRepository szakkorRepository) {

        this.szakkorRepository = szakkorRepository;
    }

    @GetMapping
    public List<Szakkor> getAllSzakkorok() {

        return szakkorRepository.findAll();

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
        return szakkorRepository.save(szakkor);
    }
    @DeleteMapping("/{id}/del")
    public String torles(@PathVariable Integer id){
        Szakkor szakkor = szakkorRepository.findById(id).orElse(null);
        if(szakkor == null){
            return "Nincs ilyen szakkör";
        }
        szakkorRepository.delete(szakkor);
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