package hu.csongor.demo.service;

import hu.csongor.demo.dto.response.DashboardResponse;

import hu.csongor.demo.repository.UserRepository;
import hu.csongor.demo.repository.SchoolRepository;
import hu.csongor.demo.repository.SzakkorRepository;
import hu.csongor.demo.repository.JelentkezesRepository;
import hu.csongor.demo.repository.HirdetmenyRepository;
import hu.csongor.demo.repository.RatingRepository;

import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final SzakkorRepository szakkorRepository;
    private final JelentkezesRepository jelentkezesRepository;
    private final HirdetmenyRepository hirdetmenyRepository;
    private final RatingRepository ratingRepository;

    public DashboardService(
            UserRepository userRepository,
            SchoolRepository schoolRepository,
            SzakkorRepository szakkorRepository,
            JelentkezesRepository jelentkezesRepository,
            HirdetmenyRepository hirdetmenyRepository,
            RatingRepository ratingRepository) {

        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.szakkorRepository = szakkorRepository;
        this.jelentkezesRepository = jelentkezesRepository;
        this.hirdetmenyRepository = hirdetmenyRepository;
        this.ratingRepository = ratingRepository;
    }
    public DashboardResponse getDashboard() {

        return new DashboardResponse(
                userRepository.count(),
                schoolRepository.count(),
                szakkorRepository.count(),
                jelentkezesRepository.count(),
                hirdetmenyRepository.count(),
                ratingRepository.count()
        );
    }
}