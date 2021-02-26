package nl.randomstuff.eindopdracht.controller;

import nl.randomstuff.eindopdracht.model.Venue;
import nl.randomstuff.eindopdracht.payload.request.ReservationRequest;
import nl.randomstuff.eindopdracht.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/venue")
@RestController
//@CrossOrigin(origins = "http://localhost:3001")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('VENUE') or hasRole('ADMIN')")
    @GetMapping("/all")//TODO: compact this list to bare necessities
    public ResponseEntity<?> getVenues() {
        return venueService.getAllVenues();
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('VENUE') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getVenueData(@PathVariable("id") long id) {
        return venueService.getVenueDataResponse(id);
    }

    @PreAuthorize("hasRole('VENUE')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteVenue(@RequestHeader("Authorization") String bearerToken) {
        return venueService.deleteVenue(bearerToken);
    }

    @PreAuthorize("hasRole('VENUE')")
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateVenue(@RequestHeader("Authorization") String bearerToken, @RequestBody Venue venue) {
        return venueService.updateVenue(bearerToken, venue);
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('VENUE') or hasRole('ADMIN')")
    @PostMapping(value = "/add_reservation")
    public ResponseEntity<?> addReservation(@RequestBody ReservationRequest reservationRequest) {
        return venueService.addReservation(reservationRequest);
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('VENUE') or hasRole('ADMIN')")
    @GetMapping(value = "/{id}/reservations")
    public ResponseEntity<?> getVenueReservations(@PathVariable("id") long id){
        return venueService.getVenueReservationsByVenueId(id);
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('VENUE') or hasRole('ADMIN')")
    @GetMapping(value = "{id}/availability")
    public ResponseEntity<?> getVenueAvailability(@PathVariable("id") long id) {
        return venueService.getVenueAvailability(id);
    }

}
