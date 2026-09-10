package hu.csongor.demo.repository;

import hu.csongor.demo.entity.Szakkor;
import hu.csongor.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  SzakkorRepository extends JpaRepository<Szakkor, Long>{
    boolean existsByNev(String nev);

}