package hu.csongor.demo.service;

import hu.csongor.demo.dto.response.JelentkezoResponse;
import hu.csongor.demo.dto.response.SzakkorResponse;
import hu.csongor.demo.entity.Jelentkezes;
import hu.csongor.demo.entity.School;
import hu.csongor.demo.entity.Szakkor;
import hu.csongor.demo.entity.User;
import hu.csongor.demo.repository.JelentkezesRepository;
import hu.csongor.demo.repository.SchoolRepository;
import hu.csongor.demo.repository.SzakkorRepository;
import hu.csongor.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class SzakkorService {

    private final SzakkorRepository szakkorRepository;
    private final SchoolRepository schoolRepository;
    private final JelentkezesRepository jelentkezesRepository;
    private final UserRepository userRepository;

    public SzakkorService(
            SzakkorRepository szakkorRepository,
            SchoolRepository schoolRepository,
            JelentkezesRepository jelentkezesRepository,
            UserRepository userRepository) {

        this.szakkorRepository = szakkorRepository;
        this.schoolRepository = schoolRepository;
        this.jelentkezesRepository = jelentkezesRepository;
        this.userRepository = userRepository;
    }
    public Object createSzakkor(
            Szakkor szakkor) {

        if(szakkor.getName() == null ||
                szakkor.getName().isBlank()) {

            return "A szakkör neve kötelező!";
        }

        if(szakkorRepository.existsByName(
                szakkor.getName())) {

            return "Már létezik ilyen szakkör";
        }

        if(szakkor.getHelyszin() == null ||
                szakkor.getHelyszin().isBlank()) {

            return "A szakkör helyszíne kötelező!";
        }

        if(szakkor.getLeiras() == null ||
                szakkor.getLeiras().isBlank()) {

            return "A szakkör leírása kötelező!";
        }

        if(szakkor.getIdopont() == null ||
                szakkor.getIdopont().isBlank()) {

            return "A szakkör időpontját megadni kötelező!";
        }

        if(szakkor.getMaxLetszam() <= 0) {

            return "A maximális létszám nem lehet 0!";
        }


        School school = schoolRepository
                .findById(szakkor.getSchoolId())
                .orElse(null);

        if(school == null) {
            return "Nincs ilyen iskola!";
        }

        if(!school.getStatusz()
                .equals("approved")) {

            return "Az iskola nincs jóváhagyva!";
        }
        User teacher = userRepository
                .findById(
                        szakkor.getTeacherId()
                )
                .orElse(null);

        if (teacher == null) {
            return "Nincs ilyen tanár!";
        }

        if (!teacher.getRole()
                .equals("TANAR")) {

            return "Csak tanár hozhat létre szakkört!";
        }

        szakkor.setCreatedAt(
                LocalDateTime.now().withNano(0)
        );

        szakkor.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        szakkor.setDeleted(false);

        return szakkorRepository.save(
                szakkor
        );
    }
    public List<JelentkezoResponse> getJelentkezok(
            Integer id) {

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
    public String torles(Integer id){

        Szakkor szakkor = szakkorRepository
                .findById(id)
                .orElse(null);

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
    public List<Szakkor> getTeacherSzakkorok(
            Integer teacherId) {

        return szakkorRepository
                .findByTeacherIdAndDeletedFalse(
                        teacherId
                );
    }
    public String updateSzakkor(
            Integer id,
            Szakkor ujAdatok) {

        Szakkor szakkor = szakkorRepository
                .findById(id)
                .orElse(null);

        if(szakkor == null){
            return "Nincs ilyen szakkör!";
        }

        szakkor.setName(
                ujAdatok.getName()
        );

        szakkor.setLeiras(
                ujAdatok.getLeiras()
        );

        szakkor.setIdopont(
                ujAdatok.getIdopont()
        );

        szakkor.setHelyszin(
                ujAdatok.getHelyszin()
        );

        szakkor.setMaxLetszam(
                ujAdatok.getMaxLetszam()
        );

        szakkor.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        szakkorRepository.save(szakkor);

        return "Szakkör módosítva!";
    }
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
                        jelentkezesRepository.countBySzakkorId(
                                szakkor.getId()
                        )
                ))
                .toList();
    }
    public List<Szakkor> getSchoolSzakkorok(
            Integer id) {

        return szakkorRepository.findBySchoolId(id);
    }

}
