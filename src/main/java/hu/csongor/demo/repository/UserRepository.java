package hu.csongor.demo.repository;

import hu.csongor.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserRepository extends JpaRepository<User, Long>{
    boolean existsByNev(String nev);
    User findByNev(String nev);
}
