package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.model.Reservation;
import nl.randomstuff.eindopdracht.model.Venue;
import nl.randomstuff.eindopdracht.payload.request.ReservationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

public interface VenueService {

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    ResponseEntity<?> getAllVenues();

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('VENUE') or hasRole('ADMIN')")
    ResponseEntity<?> getVenueDataResponse(long id);

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    ResponseEntity<?> deleteVenue(long id);

    ResponseEntity<?> saveVenue(Venue venue);

    @PreAuthorize("hasRole('VENUE') or hasRole('ADMIN')")
    ResponseEntity<?> updateVenue(String bearerToken, Venue venue);

    Venue getVenueEntityThroughUserId(String id);

    Venue getVenueEntityThroughVenueId(long id);

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('VENUE') or hasRole('ADMIN')")
    ResponseEntity<?> addReservation(ReservationRequest reservationRequest);

    @PreAuthorize("hasRole('VENUE') or hasRole('ADMIN')")
    ResponseEntity<?> getVenueReservationsByVenueId(long id);

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('VENUE') or hasRole('ADMIN')")
    ResponseEntity<?> getVenueAvailability(long id);
}
