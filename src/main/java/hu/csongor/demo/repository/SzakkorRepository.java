package hu.csongor.demo.repository;

import hu.csongor.demo.entity.Szakkor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SzakkorRepository
        extends JpaRepository<Szakkor, Integer> {

    boolean existsByName(String name);
    List<Szakkor> findBySchoolId(Integer schoolId);
    List<Szakkor> findByDeleted(Boolean deleted);
}