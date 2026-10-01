package hu.csongor.demo.service;

import hu.csongor.demo.dto.response.RatingResponse;
import hu.csongor.demo.entity.Rating;
import hu.csongor.demo.entity.Szakkor;
import hu.csongor.demo.entity.User;
import hu.csongor.demo.repository.RatingRepository;
import hu.csongor.demo.repository.SzakkorRepository;
import hu.csongor.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final SzakkorRepository szakkorRepository;

    public RatingService(
            RatingRepository ratingRepository,
            UserRepository userRepository,
            SzakkorRepository szakkorRepository) {

        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.szakkorRepository = szakkorRepository;
    }

    public Object createRating(
            Rating rating) {

        User user =
                userRepository.findById(
                        rating.getUserId()
                ).orElse(null);

        if(user == null) {
            return "Nincs ilyen felhasználó!";
        }

        Szakkor szakkor =
                szakkorRepository.findById(
                        rating.getSzakkorId()
                ).orElse(null);

        if(szakkor == null) {
            return "Nincs ilyen szakkör!";
        }

        if(rating.getStar() == null ||
                rating.getStar() < 1 ||
                rating.getStar() > 5) {

            return "Az értékelés csak 1 és 5 között lehet!";
        }

        if(rating.getOpinion() == null ||
                rating.getOpinion().isBlank()) {

            return "Vélemény megadása kötelező!";
        }

        if(ratingRepository
                .existsByUserIdAndSzakkorId(
                        rating.getUserId(),
                        rating.getSzakkorId())) {

            return "Már értékelted ezt a szakkört!";
        }

        rating.setCreatedAt(
                LocalDateTime.now().withNano(0)
        );

        rating.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        rating.setIsDeleted(false);

        return ratingRepository.save(
                rating
        );
    }

    public List<RatingResponse> getAllRatings() {

        return ratingRepository
                .findByIsDeletedFalse()
                .stream()
                .map(rating -> {

                    User user = userRepository
                            .findById(
                                    rating.getUserId()
                            )
                            .orElse(null);

                    Szakkor szakkor = szakkorRepository
                            .findById(
                                    rating.getSzakkorId()
                            )
                            .orElse(null);

                    return new RatingResponse(
                            rating.getId(),
                            rating.getStar(),
                            rating.getOpinion(),
                            user != null
                                    ? user.getName()
                                    : "Ismeretlen",
                            szakkor != null
                                    ? szakkor.getName()
                                    : "Ismeretlen",
                            rating.getCreatedAt()
                    );
                })
                .toList();
    }
    public List<RatingResponse> getRatingsBySzakkorId(
            Integer szakkorId) {

        return ratingRepository
                .findBySzakkorIdAndIsDeletedFalse(
                        szakkorId
                )
                .stream()
                .map(rating -> {

                    User user = userRepository
                            .findById(
                                    rating.getUserId()
                            )
                            .orElse(null);

                    Szakkor szakkor = szakkorRepository
                            .findById(
                                    rating.getSzakkorId()
                            )
                            .orElse(null);

                    return new RatingResponse(
                            rating.getId(),
                            rating.getStar(),
                            rating.getOpinion(),
                            user != null
                                    ? user.getName()
                                    : "Ismeretlen",
                            szakkor != null
                                    ? szakkor.getName()
                                    : "Ismeretlen",
                            rating.getCreatedAt()
                    );
                })
                .toList();
    }
    public Double getAtlagErtekeles(
            Integer szakkorId) {

        List<Rating> ratings =
                ratingRepository
                        .findBySzakkorIdAndIsDeletedFalse(
                                szakkorId
                        );

        if(ratings.isEmpty()) {
            return 0.0;
        }

        double osszeg = ratings.stream()
                .mapToInt(Rating::getStar)
                .sum();

        return osszeg / ratings.size();
    }
    public String torles(
            Integer id){

        Rating rating = ratingRepository
                .findById(id)
                .orElse(null);

        if(rating == null){
            return "Nincs ilyen értékelés!";
        }

        rating.setIsDeleted(true);

        rating.setDeletedAt(
                LocalDateTime.now().withNano(0)
        );

        rating.setUpdatedAt(
                LocalDateTime.now().withNano(0)
        );

        ratingRepository.save(rating);

        return "Értékelés törölve!";
    }
    public List<RatingResponse> getUserRatings(
            Integer userId) {

        return ratingRepository
                .findByUserIdAndIsDeletedFalse(
                        userId
                )
                .stream()
                .map(rating -> {

                    User user = userRepository
                            .findById(
                                    rating.getUserId()
                            )
                            .orElse(null);

                    Szakkor szakkor = szakkorRepository
                            .findById(
                                    rating.getSzakkorId()
                            )
                            .orElse(null);

                    return new RatingResponse(
                            rating.getId(),
                            rating.getStar(),
                            rating.getOpinion(),
                            user != null
                                    ? user.getName()
                                    : "Ismeretlen",
                            szakkor != null
                                    ? szakkor.getName()
                                    : "Ismeretlen",
                            rating.getCreatedAt()
                    );
                })
                .toList();
    }
}
