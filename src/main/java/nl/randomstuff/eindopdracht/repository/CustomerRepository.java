package nl.randomstuff.eindopdracht.repository;

import nl.randomstuff.eindopdracht.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
