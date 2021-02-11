package nl.randomstuff.eindopdracht.controller;

import nl.randomstuff.eindopdracht.model.Reservation;
import nl.randomstuff.eindopdracht.model.Venue;
import nl.randomstuff.eindopdracht.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<?> getVenue(@PathVariable("id") long id) {
        return venueService.getVenueById(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteVenue(@PathVariable("id") long id) {
        return venueService.deleteVenue(id);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> saveVenue(@RequestBody Venue venue) {
        return venueService.saveVenue(venue);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateVenue(@PathVariable("id") long id, @RequestBody Venue venue) {
        return venueService.updateVenue(id, venue);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<?> addReservation(@PathVariable("id") long id, @RequestBody Reservation reservation) {
        return venueService.addReservation(id, reservation);
    }

    @GetMapping(value = "/{id}/availability")
    public ResponseEntity<?> getVenueReservations(@PathVariable("id") long id){
        return venueService.getVenueReservationsByVenueId(id);
    }

}
