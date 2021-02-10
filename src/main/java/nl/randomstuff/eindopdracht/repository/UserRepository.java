package nl.randomstuff.eindopdracht.repository;

import nl.randomstuff.eindopdracht.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
