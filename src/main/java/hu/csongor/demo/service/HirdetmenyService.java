package hu.csongor.demo.service;

import hu.csongor.demo.entity.Hirdetmeny;
import hu.csongor.demo.entity.School;
import hu.csongor.demo.entity.User;

import hu.csongor.demo.repository.HirdetmenyRepository;
import hu.csongor.demo.repository.SchoolRepository;
import hu.csongor.demo.repository.UserRepository;
import hu.csongor.demo.dto.response.HirdetmenyResponse;

import java.util.List;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HirdetmenyService {
    private final HirdetmenyRepository hirdetmenyRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    public HirdetmenyService(
            HirdetmenyRepository hirdetmenyRepository,
            UserRepository userRepository,
            SchoolRepository schoolRepository) {

        this.hirdetmenyRepository = hirdetmenyRepository;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
    }
    public Object createHirdetmeny(
            Hirdetmeny hirdetmeny) {


        if(hirdetmeny.getTitle() == null ||
                hirdetmeny.getTitle().isBlank()) {

            return "A cím megadása kötelező!";
        }

        if(hirdetmeny.getContent() == null ||
                hirdetmeny.getContent().isBlank()) {

            return "A tartalom megadása kötelező!";
        }

        User teacher = userRepository
                .findById(
                        hirdetmeny.getTeacherId()
                )
                .orElse(null);

        if(teacher == null) {

            return "Nincs ilyen tanár!";
        }

        if(!teacher.getRole()
                .equals("TANAR")) {

            return "Csak tanár hozhat létre hirdetményt!";
        }

        School school = schoolRepository
                .findById(
                        hirdetmeny.getSchoolId()
                )
                .orElse(null);

        if(school == null) {

            return "Nincs ilyen iskola!";
        }

        if(Boolean.TRUE.equals(
                school.getIsDeleted())) {

            return "Az iskola törölve van!";
        }

        hirdetmeny.setCreatedAt(
                LocalDateTime.now().withNano(0)
        );

        hirdetmeny.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        hirdetmeny.setIsDeleted(false);

        return hirdetmenyRepository.save(
                hirdetmeny
        );
    }
    public List<HirdetmenyResponse>
    getAllHirdetmenyek() {

        List<Hirdetmeny> hirdetmenyek =
                hirdetmenyRepository
                        .findByIsDeletedFalse();

        return hirdetmenyek.stream()
                .map(hirdetmeny ->
                        new HirdetmenyResponse(
                                hirdetmeny.getId(),
                                hirdetmeny.getTitle(),
                                hirdetmeny.getContent(),
                                hirdetmeny.getTeacherId(),
                                hirdetmeny.getSchoolId(),
                                hirdetmeny.getCreatedAt()
                        ))
                .toList();
    }
    public List<HirdetmenyResponse>
    getSchoolHirdetmenyek(
            Integer schoolId) {

        return hirdetmenyRepository
                .findBySchoolIdAndIsDeletedFalse(
                        schoolId
                )
                .stream()
                .map(hirdetmeny ->
                        new HirdetmenyResponse(
                                hirdetmeny.getId(),
                                hirdetmeny.getTitle(),
                                hirdetmeny.getContent(),
                                hirdetmeny.getTeacherId(),
                                hirdetmeny.getSchoolId(),
                                hirdetmeny.getCreatedAt()
                        ))
                .toList();
    }
    public List<HirdetmenyResponse>
    getTeacherHirdetmenyek(
            Integer teacherId) {

        return hirdetmenyRepository
                .findByTeacherIdAndIsDeletedFalse(
                        teacherId
                )
                .stream()
                .map(hirdetmeny ->
                        new HirdetmenyResponse(
                                hirdetmeny.getId(),
                                hirdetmeny.getTitle(),
                                hirdetmeny.getContent(),
                                hirdetmeny.getTeacherId(),
                                hirdetmeny.getSchoolId(),
                                hirdetmeny.getCreatedAt()
                        ))
                .toList();
    }
    public String updateHirdetmeny(
            Integer id,
            Hirdetmeny ujAdatok) {

        Hirdetmeny hirdetmeny =
                hirdetmenyRepository
                        .findById(id)
                        .orElse(null);

        if(hirdetmeny == null) {
            return "Nincs ilyen hirdetmény!";
        }

        if(!hirdetmeny.getTeacherId()
                .equals(ujAdatok.getTeacherId())) {

            return "Csak a létrehozó tanár módosíthatja a hirdetményt!";
        }

        if(ujAdatok.getTitle() == null ||
                ujAdatok.getTitle().isBlank()) {

            return "A cím megadása kötelező!";
        }

        if(ujAdatok.getContent() == null ||
                ujAdatok.getContent().isBlank()) {

            return "A tartalom megadása kötelező!";
        }

        hirdetmeny.setTitle(
                ujAdatok.getTitle()
        );

        hirdetmeny.setContent(
                ujAdatok.getContent()
        );

        hirdetmeny.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        hirdetmenyRepository.save(
                hirdetmeny
        );

        return "Hirdetmény módosítva!";
    }

    public String torles(
            Integer id) {

        Hirdetmeny hirdetmeny =
                hirdetmenyRepository
                        .findById(id)
                        .orElse(null);

        if(hirdetmeny == null) {
            return "Nincs ilyen hirdetmény!";
        }

        hirdetmeny.setIsDeleted(true);

        hirdetmeny.setDeletedAt(
                LocalDateTime.now().withNano(0)
        );

        hirdetmeny.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        hirdetmenyRepository.save(
                hirdetmeny
        );

        return "Hirdetmény törölve!";
    }

}
