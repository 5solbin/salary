package solbin.project.salary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solbin.project.salary.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
