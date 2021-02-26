package nl.randomstuff.eindopdracht.repository;

import nl.randomstuff.eindopdracht.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
