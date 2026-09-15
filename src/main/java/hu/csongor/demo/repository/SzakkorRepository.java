package hu.csongor.demo.repository;

import hu.csongor.demo.entity.Szakkor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SzakkorRepository
        extends JpaRepository<Szakkor, Integer> {

    boolean existsByName(String name);

}