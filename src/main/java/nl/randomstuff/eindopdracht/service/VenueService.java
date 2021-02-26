package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.model.Venue;
import nl.randomstuff.eindopdracht.payload.request.ReservationRequest;
import org.springframework.http.ResponseEntity;

public interface VenueService {

    ResponseEntity<?> getAllVenues();

    ResponseEntity<?> getVenueDataResponse(long id);

    ResponseEntity<?> deleteVenue(String bearerToken);

    Venue saveVenueInDb(Venue venue);

    ResponseEntity<?> updateVenue(String bearerToken, Venue venue);

    Venue getVenueEntityThroughUserId(String id);

    Venue getVenueEntityThroughVenueId(long id);

    ResponseEntity<?> addReservation(ReservationRequest reservationRequest);

    ResponseEntity<?> getVenueReservationsByVenueId(long id);

    ResponseEntity<?> getVenueAvailability(long id);
}
