package hu.csongor.demo.repository;

import hu.csongor.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository
        extends JpaRepository<User, Integer> {

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    User findByName(String name);

    Integer countByRole(String role);
    List<User> findByIsDeleted(Boolean isDeleted);

    Integer countByIsBanned(Boolean isBanned);

}