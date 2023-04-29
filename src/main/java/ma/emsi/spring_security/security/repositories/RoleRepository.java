package ma.emsi.spring_security.security.repositories;

import ma.emsi.spring_security.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
