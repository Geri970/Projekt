package hu.csongor.demo.repository;

import hu.csongor.demo.entity.Jelentkezes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JelentkezesRepository
        extends JpaRepository<Jelentkezes, Long> {
    boolean existsByUserIdAndSzakkorId(
            Long userId,
            Long szakkorId
    );
}