package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.model.Reservation;
import nl.randomstuff.eindopdracht.model.Venue;
import org.springframework.http.ResponseEntity;

public interface VenueService {

    ResponseEntity<?> getAllVenues();

    ResponseEntity<?> getVenueById(long id);

    ResponseEntity<?> deleteVenue(long id);

    ResponseEntity<?> saveVenue(Venue venue);

    ResponseEntity<?> updateVenue(long id, Venue venue);

    Venue getVenueEntity(long id);

    ResponseEntity<?> addReservation(long id, Reservation reservation);

    ResponseEntity<?> getVenueReservationsByVenueId(long id);

    ResponseEntity<?> getVenueAvailability(long id);
}
