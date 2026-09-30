package hu.csongor.demo.controller;

import hu.csongor.demo.entity.School;
import hu.csongor.demo.service.SchoolService;
import hu.csongor.demo.dto.response.SchoolResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/school")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(
            SchoolService schoolService) {

        this.schoolService = schoolService;
    }

    @PostMapping
    public Object createSchool(
            @RequestBody School school){

        return schoolService.createSchool(
                school
        );
    }

    @GetMapping
    public List<SchoolResponse> getAllSchools(){
        return schoolService.getAllSchools();
    }
    @GetMapping("/PENDING")
    public List<SchoolResponse> getAllSchoolsPending(){
        return schoolService.getAllSchoolsPending();
    }
    @GetMapping("/APPROVED")
    public List<SchoolResponse> getAllSchoolsApproved(){
        return schoolService.getAllSchoolsApproved();
    }
    @GetMapping("/REJECTED")
    public List<SchoolResponse> getAllSchoolsRejected(){
        return schoolService.getAllSchoolsRejected();
    }
    @PutMapping("/{id}/approve")
    public String approveSchool(
            @PathVariable Integer id){

        return schoolService.approveSchool(
                id
        );
    }
    @PutMapping("/{id}/reject")
    public String rejectSchool(
            @PathVariable Integer id){

        return schoolService.rejectSchool(
                id
        );
    }
    @DeleteMapping("/{id}")
    public String torles(
            @PathVariable Integer id){

        return schoolService.torles(id);
    }
}