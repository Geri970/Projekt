package hu.csongor.demo.repository;

import hu.csongor.demo.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SchoolRepository
        extends JpaRepository<School, Long> {

    boolean existsByNev(String nev);
    List<School> findByStatusz(
            String statusz
    );
}