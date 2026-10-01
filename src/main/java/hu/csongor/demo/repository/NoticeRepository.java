package hu.csongor.demo.repository;

import hu.csongor.demo.entity.Hirdetmeny;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HirdetmenyRepository
        extends JpaRepository<Hirdetmeny, Integer> {

    List<Hirdetmeny> findByIsDeletedFalse();

    List<Hirdetmeny> findBySchoolIdAndIsDeletedFalse(
            Integer schoolId
    );

    List<Hirdetmeny> findByTeacherIdAndIsDeletedFalse(
            Integer teacherId
    );
}