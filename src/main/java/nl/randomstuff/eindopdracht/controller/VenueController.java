package nl.randomstuff.eindopdracht.controller;

import nl.randomstuff.eindopdracht.model.Reservation;
import nl.randomstuff.eindopdracht.model.Venue;
import nl.randomstuff.eindopdracht.payload.request.ReservationRequest;
import nl.randomstuff.eindopdracht.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/venue")
@RestController
//@CrossOrigin(origins = "http://localhost:3001")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping("/all")
    public ResponseEntity<?> getVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVenueData(@PathVariable("id") long id) {
        return venueService.getVenueDataResponse(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteVenue(@PathVariable("id") long id) {
        return venueService.deleteVenue(id);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateVenue(@RequestHeader("Authorization") String bearerToken, @RequestBody Venue venue) {
        return venueService.updateVenue(bearerToken, venue);
    }

    @PostMapping(value = "/add_reservation")
    public ResponseEntity<?> addReservation(@RequestBody ReservationRequest reservationRequest) {
        return venueService.addReservation(reservationRequest);
    }

    @GetMapping(value = "/{id}/reservations")
    public ResponseEntity<?> getVenueReservations(@PathVariable("id") long id){
        return venueService.getVenueReservationsByVenueId(id);
    }

    @GetMapping(value = "{id}/availability")
    public ResponseEntity<?> getVenueAvailability(@PathVariable("id") long id) {
        return venueService.getVenueAvailability(id);
    }

}
