package hu.csongor.demo.repository;

import hu.csongor.demo.entity.Jelentkezes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface JelentkezesRepository
        extends JpaRepository<Jelentkezes, Integer> {
    boolean existsByUserIdAndSzakkorId(
            Integer userId,
            Integer szakkorId
    );
    List<Jelentkezes> findByUserId(Integer userId);
    List<Jelentkezes> findBySzakkorId(Integer szakkorId);
    long countBySzakkorId(Integer szakkorId);
}