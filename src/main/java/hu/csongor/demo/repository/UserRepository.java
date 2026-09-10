package hu.csongor.demo.repository;

import hu.csongor.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserRepository extends JpaRepository<User, Long>{
    boolean existsByNev(String nev);
    boolean existsByEmail(String email);
    User findByNev(String nev);
    long  countByRole(String role);
    long countByTiltva(boolean tiltva);
}
