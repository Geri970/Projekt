package hu.csongor.demo.repository;

import hu.csongor.demo.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository
        extends JpaRepository<Rating, Integer> {

    List<Rating> findByIsDeletedFalse();

    List<Rating> findBySzakkorIdAndIsDeletedFalse(
            Integer szakkorId);

    List<Rating> findByUserIdAndIsDeletedFalse(
            Integer userId);

    boolean existsByUserIdAndSzakkorId(
            Integer userId,
            Integer szakkorId);

}