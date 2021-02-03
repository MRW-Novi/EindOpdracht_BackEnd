package nl.randomstuff.eindopdracht.repository;

import nl.randomstuff.eindopdracht.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
