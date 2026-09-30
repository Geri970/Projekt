package hu.csongor.demo.service;

import hu.csongor.demo.dto.response.SchoolResponse;
import hu.csongor.demo.entity.School;
import hu.csongor.demo.repository.SchoolRepository;
import hu.csongor.demo.repository.SzakkorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final SzakkorRepository szakkorRepository;

    public SchoolService(
            SchoolRepository schoolRepository,
            SzakkorRepository szakkorRepository) {

        this.schoolRepository = schoolRepository;
        this.szakkorRepository = szakkorRepository;
    }

    public Object createSchool(
            School school){

        if(school.getName() == null ||
                school.getName().isBlank()){

            return "Az iskola név kötelező!";
        }

        if(school.getVaros() == null ||
                school.getVaros().isBlank()){

            return "Város megadása kötelező!";
        }

        if(school.getLeiras() == null ||
                school.getLeiras().isBlank()){

            return "Az iskola leírása kötelező!";
        }

        if(schoolRepository.existsByName(
                school.getName())){

            return "Már létezik ilyen iskola!";
        }

        school.setStatusz("PENDING");

        school.setCreatedAt(
                LocalDateTime.now().withNano(0)
        );

        school.setIsDeleted(false);

        return schoolRepository.save(
                school
        );
    }

    public List<SchoolResponse> getAllSchools(){

        List<School> schools =
                schoolRepository.findByIsDeletedFalse();

        return schools.stream()
                .map(school -> new SchoolResponse(
                        school.getId(),
                        school.getName(),
                        school.getVaros(),
                        school.getStatusz(),
                        szakkorRepository.countBySchoolId(
                                school.getId()
                        )
                ))
                .toList();
    }

    public List<SchoolResponse> getAllSchoolsPending(){

        return schoolRepository
                .findByStatuszAndIsDeletedFalse(
                        "PENDING"
                )
                .stream()
                .map(school -> new SchoolResponse(
                        school.getId(),
                        school.getName(),
                        school.getVaros(),
                        school.getStatusz(),
                        szakkorRepository.countBySchoolId(
                                school.getId()
                        )
                ))
                .toList();
    }

    public List<SchoolResponse> getAllSchoolsApproved(){

        return schoolRepository
                .findByStatuszAndIsDeletedFalse(
                        "APPROVED"
                )
                .stream()
                .map(school -> new SchoolResponse(
                        school.getId(),
                        school.getName(),
                        school.getVaros(),
                        school.getStatusz(),
                        szakkorRepository.countBySchoolId(
                                school.getId()
                        )
                ))
                .toList();
    }

    public List<SchoolResponse> getAllSchoolsRejected(){

        return schoolRepository
                .findByStatuszAndIsDeletedFalse(
                        "REJECTED"
                )
                .stream()
                .map(school -> new SchoolResponse(
                        school.getId(),
                        school.getName(),
                        school.getVaros(),
                        school.getStatusz(),
                        szakkorRepository.countBySchoolId(
                                school.getId()
                        )
                ))
                .toList();
    }

    public String approveSchool(
            Integer id){

        School school = schoolRepository
                .findById(id)
                .orElse(null);

        if(school == null){
            return "Nincs ilyen iskola";
        }

        school.setStatusz("APPROVED");

        school.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        schoolRepository.save(
                school
        );

        return "Az iskola jóváhagyva";
    }

    public String rejectSchool(
            Integer id){

        School school = schoolRepository
                .findById(id)
                .orElse(null);

        if(school == null){
            return "Nincs ilyen iskola";
        }

        school.setStatusz("REJECTED");

        school.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        schoolRepository.save(
                school
        );

        return "Az iskola el lett utasítva";
    }
    public String torles(Integer id){

        School school = schoolRepository
                .findById(id)
                .orElse(null);

        if(school == null){
            return "Nincs ilyen iskola!";
        }

        school.setIsDeleted(true);

        school.setDeletedAt(
                LocalDateTime.now().withNano(0)
        );

        school.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        schoolRepository.save(school);

        return "Iskola törölve!";
    }
}