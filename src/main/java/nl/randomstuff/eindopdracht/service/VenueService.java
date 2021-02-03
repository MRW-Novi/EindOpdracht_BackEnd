package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.model.Reservation;
import nl.randomstuff.eindopdracht.model.Venue;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VenueService {

    ResponseEntity<?> getAllVenues();

    ResponseEntity<?> getVenueById(long id);

    ResponseEntity<?> deleteVenue(long id);

    ResponseEntity<?> saveVenue(Venue venue);

    ResponseEntity<?> updateVenue(long id, Venue venue);

    ResponseEntity<?> getVenueData(long id);

    ResponseEntity<?> addReservation(long id, Reservation reservation);
}
