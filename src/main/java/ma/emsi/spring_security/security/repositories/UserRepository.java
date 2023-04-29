package ma.emsi.spring_security.security.repositories;

import ma.emsi.spring_security.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
