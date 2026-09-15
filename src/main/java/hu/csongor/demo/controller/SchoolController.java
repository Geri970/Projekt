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

        if(school.getName() == null ||
                school.getName().isBlank()){

            return "Az iskola Namee kötelező!";
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
                .existsByName(
                        school.getName())){

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
    @GetMapping("/APPROVED")
    public List<School> getAllSchoolsApproved(){
        return schoolRepository.findByStatusz("APPROVED");
    }
    @GetMapping("/REJECTED")
    public List<School> getAllSchoolsRejected(){
        return schoolRepository.findByStatusz("REJECTED");
    }
    @PutMapping("/{id}/approve")
    public String approveSchool(
                @PathVariable Integer id){
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
            @PathVariable Integer id){
        School school = schoolRepository.findById(id).orElse(null);
        if(school == null){
            return "Nincs ilyen iskola";
        }
        school.setStatusz("REJECTED");
        schoolRepository.save(school);
        return "Az iskola el lett utasítva";
    }
}