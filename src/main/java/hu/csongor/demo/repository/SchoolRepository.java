package hu.csongor.demo.repository;

import hu.csongor.demo.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository
        extends JpaRepository<School, Integer> {

    boolean existsByName(String name);

    List<School> findByStatuszAndIsDeletedFalse(
            String statusz);
    List<School> findByIsDeletedFalse();
}