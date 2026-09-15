package hu.csongor.demo.repository;

import hu.csongor.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<User, Integer> {

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    User findByName(String name);

    Integer countByRole(String role);

    Integer countByIsBanned(Boolean isBanned);

}