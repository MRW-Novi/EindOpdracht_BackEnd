package nl.randomstuff.eindopdracht.repository;

import nl.randomstuff.eindopdracht.model.ERole;
import nl.randomstuff.eindopdracht.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
