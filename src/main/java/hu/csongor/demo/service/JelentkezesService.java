package hu.csongor.demo.service;

import hu.csongor.demo.entity.Jelentkezes;
import hu.csongor.demo.entity.Szakkor;
import hu.csongor.demo.entity.User;
import hu.csongor.demo.repository.JelentkezesRepository;
import hu.csongor.demo.repository.SzakkorRepository;
import hu.csongor.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JelentkezesService {

    private final JelentkezesRepository jelentkezesRepository;
    private final UserRepository userRepository;
    private final SzakkorRepository szakkorRepository;

    public JelentkezesService(
            JelentkezesRepository jelentkezesRepository,
            UserRepository userRepository,
            SzakkorRepository szakkorRepository) {

        this.jelentkezesRepository = jelentkezesRepository;
        this.userRepository = userRepository;
        this.szakkorRepository = szakkorRepository;
    }

    public Object jelentkezes(
            Jelentkezes jelentkezes) {

        if (!userRepository.existsById(
                jelentkezes.getUserId())) {

            return "Nincs ilyen felhasználó!";
        }

        User user = userRepository
                .findById(
                        jelentkezes.getUserId())
                .orElse(null);

        if(Boolean.TRUE.equals(
                user.getIsDeleted())) {

            return "A felhasználó törölve van!";
        }

        if(Boolean.TRUE.equals(
                user.getIsBanned())) {

            return "A felhasználó tiltva van!";
        }

        if (!szakkorRepository.existsById(
                jelentkezes.getSzakkorId())) {

            return "Nincs ilyen szakkör!";
        }

        Szakkor szakkor = szakkorRepository
                .findById(
                        jelentkezes.getSzakkorId())
                .orElse(null);

        if(Boolean.TRUE.equals(
                szakkor.getDeleted())) {

            return "A szakkör törölve van!";
        }

        if (jelentkezesRepository
                .existsByUserIdAndSzakkorId(
                        jelentkezes.getUserId(),
                        jelentkezes.getSzakkorId())) {

            return "Már jelentkeztél erre a szakkörre!";
        }

        long letszam =
                jelentkezesRepository
                        .countBySzakkorId(
                                jelentkezes.getSzakkorId());

        if (letszam >=
                szakkor.getMaxLetszam()) {

            return "A szakkör betelt!";
        }

        jelentkezes.setJelentkezesDatum(
                LocalDateTime.now()
                        .withNano(0));

        return jelentkezesRepository
                .save(jelentkezes);
    }

    public List<Jelentkezes>
    getAllJelentkezes() {

        return jelentkezesRepository
                .findAll();
    }

    public Map<String, Long> getJelentkezokSzama(Integer id) {

        Map<String, Long> map = new HashMap<>();

        map.put(
                "jelentkezok",
                jelentkezesRepository.countBySzakkorId(id)
        );

        return map;
    }

    public List<Jelentkezes> getUserJelentkezesek(Integer id) {
        return jelentkezesRepository.findByUserId(id);
    }

    public List<Jelentkezes> getSzakkorJelentkezoi(Integer id) {
        return jelentkezesRepository.findBySzakkorId(id);
    }

    public String torles(Integer id) {

        Jelentkezes jelentkezes = jelentkezesRepository
                .findById(id)
                .orElse(null);

        if (jelentkezes == null) {
            return "Nincs ilyen jelentkezés!";
        }

        jelentkezesRepository.delete(jelentkezes);

        return "Jelentkezés törölve!";
    }
}