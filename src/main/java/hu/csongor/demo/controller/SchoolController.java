package hu.csongor.demo.controller;

import hu.csongor.demo.entity.School;
import hu.csongor.demo.repository.SchoolRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/school")
public class SchoolController {

    private final SchoolRepository schoolRepository;

    public SchoolController(
            SchoolRepository schoolRepository) {

        this.schoolRepository = schoolRepository;
    }
    @PostMapping
    public Object createSchool(
            @RequestBody School school){

        if(school.getNev() == null ||
                school.getNev().isBlank()){

            return "Az iskola neve kötelező!";
        }
        if(school.getVaros() == null ||
                school.getVaros().isBlank()){

            return "Város megadása kötelező!";
        }
        if(school.getLeiras() == null ||
                school.getLeiras().isBlank()){

            return "Az iskola leírása kötelező!";
        }
        if(schoolRepository
                .existsByNev(
                        school.getNev())){

            return "Már létezik ilyen iskola!";
        }

        school.setStatusz("PENDING");

        return schoolRepository.save(
                school
        );
    }

    @GetMapping
    public List<School> getAllSchools(){

        return schoolRepository.findAll();

    }
    @GetMapping("/PENDING")
    public List<School> getAllSchoolsPending(){
        return schoolRepository.findByStatusz("PENDING");
    }
    @PutMapping("/{id}/approve")
    public String approveSchool(
                @PathVariable Long id){
        School school = schoolRepository.findById(id).orElse(null);
        if(school == null){
            return "Nincs ilyen iskola";
        }
        school.setStatusz("APPROVED");
        schoolRepository.save(school);
        return "Az iskola jóváhagyva";
    }
    @PutMapping("/{id}/reject")
    public String rejectSchool(
            @PathVariable Long id){
        School school = schoolRepository.findById(id).orElse(null);
        if(school == null){
            return "Nincs ilyen iskola";
        }
        school.setStatusz("REJECTED");
        schoolRepository.save(school);
        return "Az iskola el lett utasítva";
    }
}