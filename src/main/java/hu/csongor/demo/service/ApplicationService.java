package hu.csongor.demo.service;

import hu.csongor.demo.entity.Club;
import hu.csongor.demo.entity.Application;
import hu.csongor.demo.entity.User;
import hu.csongor.demo.repository.ApplicationRepository;
import hu.csongor.demo.repository.ClubRepository;
import hu.csongor.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JelentkezesService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;

    public JelentkezesService(
            ApplicationRepository applicationRepository,
            UserRepository userRepository,
            ClubRepository clubRepository) {

        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.clubRepository = clubRepository;
    }

    public Object jelentkezes(
            Application application) {

        if (!userRepository.existsById(
                application.getUserId())) {

            return "Nincs ilyen felhasználó!";
        }

        User user = userRepository
                .findById(
                        application.getUserId())
                .orElse(null);

        if(Boolean.TRUE.equals(
                user.getIsDeleted())) {

            return "A felhasználó törölve van!";
        }

        if(Boolean.TRUE.equals(
                user.getIsBanned())) {

            return "A felhasználó tiltva van!";
        }

        if (!clubRepository.existsById(
                application.getSzakkorId())) {

            return "Nincs ilyen szakkör!";
        }

        Club club = clubRepository
                .findById(
                        application.getSzakkorId())
                .orElse(null);

        if(Boolean.TRUE.equals(
                club.getDeleted())) {

            return "A szakkör törölve van!";
        }

        if (applicationRepository
                .existsByUserIdAndSzakkorId(
                        application.getUserId(),
                        application.getSzakkorId())) {

            return "Már jelentkeztél erre a szakkörre!";
        }

        long letszam =
                applicationRepository
                        .countBySzakkorId(
                                application.getSzakkorId());

        if (letszam >=
                club.getMaxLetszam()) {

            return "A szakkör betelt!";
        }

        application.setJelentkezesDatum(
                LocalDateTime.now()
                        .withNano(0));

        return applicationRepository
                .save(application);
    }

    public List<Application>
    getAllJelentkezes() {

        return applicationRepository
                .findAll();
    }

    public Map<String, Long> getJelentkezokSzama(Integer id) {

        Map<String, Long> map = new HashMap<>();

        map.put(
                "jelentkezok",
                applicationRepository.countBySzakkorId(id)
        );

        return map;
    }

    public List<Application> getUserJelentkezesek(Integer id) {
        return applicationRepository.findByUserId(id);
    }

    public List<Application> getSzakkorJelentkezoi(Integer id) {
        return applicationRepository.findBySzakkorId(id);
    }

    public String torles(Integer id) {

        Application application = applicationRepository
                .findById(id)
                .orElse(null);

        if (application == null) {
            return "Nincs ilyen jelentkezés!";
        }

        applicationRepository.delete(application);

        return "Jelentkezés törölve!";
    }
}