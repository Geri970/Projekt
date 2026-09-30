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
import hu.csongor.demo.service.SzakkorService;
import hu.csongor.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/szakkor")
public class SzakkorController {

    private final SzakkorService szakkorService;

    public SzakkorController(
            SzakkorService szakkorService) {

        this.szakkorService = szakkorService;
    }



    @GetMapping
    public List<SzakkorResponse> getAllSzakkorok() {

        return szakkorService.getAllSzakkorok();
    }
    @PostMapping
    public Object createSzakkor(
            @RequestBody Szakkor szakkor) {

        return szakkorService.createSzakkor(
                szakkor
        );
    }
    @GetMapping("/teacher/{id}")
    public List<Szakkor> getTeacherSzakkorok(
            @PathVariable Integer id) {

        return szakkorService
                .getTeacherSzakkorok(id);
    }
    @GetMapping("/school/{id}")
    public List<Szakkor> getSchoolSzakkorok(
            @PathVariable Integer id) {

        return szakkorService.getSchoolSzakkorok(id);
    }
    @GetMapping("/{id}/applicants")
    public List<JelentkezoResponse> getJelentkezok(
            @PathVariable Integer id) {

        return szakkorService.getJelentkezok(id);
    }
    @DeleteMapping("/{id}/del")
    public String torles(
            @PathVariable Integer id){

        return szakkorService.torles(id);
    }
    @PutMapping("/{id}/update")
    public String updateSzakkor(
            @PathVariable Integer id,
            @RequestBody Szakkor ujAdatok) {

        return szakkorService.updateSzakkor(
                id,
                ujAdatok
        );
    }
}