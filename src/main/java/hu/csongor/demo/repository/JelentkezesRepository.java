package hu.csongor.demo.repository;

import hu.csongor.demo.entity.Jelentkezes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface JelentkezesRepository
        extends JpaRepository<Jelentkezes, Long> {
    boolean existsByUserIdAndSzakkorId(
            Long userId,
            Long szakkorId
    );
    List<Jelentkezes> findByUserId(Long userId);
    List<Jelentkezes> findBySzakkorId(Long szakkorId);
    long countBySzakkorId(Long szakkorId);
}