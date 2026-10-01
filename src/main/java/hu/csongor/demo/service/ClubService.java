package hu.csongor.demo.service;

import hu.csongor.demo.dto.response.ApplicationResponse;
import hu.csongor.demo.dto.response.ClubResponse;
import hu.csongor.demo.entity.*;
import hu.csongor.demo.repository.*;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
@Service
public class SzakkorService {

    private final ClubRepository clubRepository;
    private final SchoolRepository schoolRepository;
    private final ApplicationRepository applicationRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    public SzakkorService(
            ClubRepository clubRepository,
            SchoolRepository schoolRepository,
            ApplicationRepository applicationRepository,
            UserRepository userRepository,
            RatingRepository ratingRepository) {

        this.clubRepository = clubRepository;
        this.schoolRepository = schoolRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }
    public Object createSzakkor(
            Club club) {

        if(club.getName() == null ||
                club.getName().isBlank()) {

            return "A szakkör neve kötelező!";
        }

        if(clubRepository.existsByName(
                club.getName())) {

            return "Már létezik ilyen szakkör";
        }

        if(club.getHelyszin() == null ||
                club.getHelyszin().isBlank()) {

            return "A szakkör helyszíne kötelező!";
        }

        if(club.getLeiras() == null ||
                club.getLeiras().isBlank()) {

            return "A szakkör leírása kötelező!";
        }

        if(club.getIdopont() == null ||
                club.getIdopont().isBlank()) {

            return "A szakkör időpontját megadni kötelező!";
        }

        if(club.getMaxLetszam() <= 0) {

            return "A maximális létszám nem lehet 0!";
        }


        School school = schoolRepository
                .findById(club.getSchoolId())
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
                        club.getTeacherId()
                )
                .orElse(null);

        if (teacher == null) {
            return "Nincs ilyen tanár!";
        }

        if (!teacher.getRole()
                .equals("TANAR")) {

            return "Csak tanár hozhat létre szakkört!";
        }

        club.setCreatedAt(
                LocalDateTime.now().withNano(0)
        );

        club.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        club.setDeleted(false);

        return clubRepository.save(
                club
        );
    }
    public List<ApplicationResponse> getJelentkezok(
            Integer id) {

        List<Application> jelentkezesek =
                applicationRepository.findBySzakkorId(id);

        return jelentkezesek.stream()
                .map(application -> {

                    User user = userRepository
                            .findById(
                                    application.getUserId()
                            )
                            .orElse(null);

                    return new ApplicationResponse(
                            user.getId(),
                            user.getName(),
                            user.getEmail()
                    );
                })
                .toList();
    }
    public String torles(Integer id){

        Club club = clubRepository
                .findById(id)
                .orElse(null);

        if(club == null){
            return "Nincs ilyen szakkör";
        }

        club.setDeleted(true);

        club.setDeletedAt(
                LocalDateTime.now().withNano(0)
        );

        clubRepository.save(club);

        return "Szakkör törölve";
    }
    public List<Club> getTeacherSzakkorok(
            Integer teacherId) {

        return clubRepository
                .findByTeacherIdAndDeletedFalse(
                        teacherId
                );
    }
    public String updateSzakkor(
            Integer id,
            Club ujAdatok) {

        Club club = clubRepository
                .findById(id)
                .orElse(null);

        if(club == null){
            return "Nincs ilyen szakkör!";
        }

        club.setName(
                ujAdatok.getName()
        );

        club.setLeiras(
                ujAdatok.getLeiras()
        );

        club.setIdopont(
                ujAdatok.getIdopont()
        );

        club.setHelyszin(
                ujAdatok.getHelyszin()
        );

        club.setMaxLetszam(
                ujAdatok.getMaxLetszam()
        );

        club.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        clubRepository.save(club);

        return "Szakkör módosítva!";
    }
    public List<ClubResponse> getAllSzakkorok() {

        List<Club> szakkorok =
                clubRepository.findByDeleted(false);

        return szakkorok.stream()
                .map(szakkor -> {

                    double atlagErtekeles =
                            ratingRepository
                                    .findBySzakkorIdAndIsDeletedFalse(
                                            szakkor.getId()
                                    )
                                    .stream()
                                    .mapToInt(rating -> rating.getStar())
                                    .average()
                                    .orElse(0.0);

                    atlagErtekeles =
                            Math.round(atlagErtekeles * 10.0) / 10.0;

                    return new ClubResponse(
                            szakkor.getId(),
                            szakkor.getName(),
                            szakkor.getLeiras(),
                            szakkor.getIdopont(),
                            szakkor.getHelyszin(),
                            szakkor.getMaxLetszam(),
                            applicationRepository.countBySzakkorId(
                                    szakkor.getId()
                            ),
                            atlagErtekeles
                    );
                })
                .toList();
    }
    public List<Club> getSchoolSzakkorok(
            Integer id) {

        return clubRepository.findBySchoolId(id);
    }

}
