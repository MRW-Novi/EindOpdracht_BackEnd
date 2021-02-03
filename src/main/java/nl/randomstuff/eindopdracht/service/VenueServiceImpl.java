package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.model.Reservation;
import nl.randomstuff.eindopdracht.model.Venue;
import nl.randomstuff.eindopdracht.payload.response.VenueResponse;
import nl.randomstuff.eindopdracht.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    VenueRepository venueRepository;

    @Override
    public ResponseEntity<?> getAllVenues() {
        return ResponseEntity.status(200).body(venueRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getVenueById(long id) {

        Optional<Venue> venueFromDb = venueRepository.findById(id);

        if (venueFromDb.isPresent()){
            return ResponseEntity.status(200).body(venueRepository.findById(id));
        }
        return ResponseEntity.status(500).body("Venue with id " + id + " not found.");

    }

    @Override
    public ResponseEntity<?> deleteVenue(long id) {
        Optional<Venue> venueFromDb = venueRepository.findById(id);

        if (venueFromDb.isPresent()){
            venueRepository.deleteById(id);
            return ResponseEntity.status(200).body("Venue with id " + id + " successfully deleted");
        }
        return ResponseEntity.status(500).body("Venue with id " + id + " not found.");

    }

    @Override
    public ResponseEntity<?> saveVenue(Venue venue) {
        Venue newVenue = venueRepository.save(venue);
        return ResponseEntity.status(200).body(newVenue.getId());
    }

    @Override
    public ResponseEntity<?> updateVenue(long id, Venue venue) {

        Optional<Venue> venueFromDb = venueRepository.findById(id);

        if (venueFromDb.isPresent()) {
            venueFromDb.get().setVenueName(venue.getVenueName());
            venueFromDb.get().setVenueEmailAddress(venue.getVenueEmailAddress());
            venueFromDb.get().setVenueLocation(venue.getVenueLocation());
            return ResponseEntity.status(200).body("Venue with id " + id + " updated");
        }
        return ResponseEntity.status(500).body("Venue with id " + id + " not found.");
    }

    @Override
    public ResponseEntity<?> getVenueData(long id) {

        Optional<Venue> venueFromDb = venueRepository.findById(id);

        VenueResponse venueResponse = new VenueResponse();
        if (venueFromDb.isPresent()) {
            venueResponse.setName(venueFromDb.get().getVenueName());
            return ResponseEntity.status(200).body(venueResponse);
        }

        return ResponseEntity.status(500).body("Venue with id " + id + " not found.");
    }

    @Override
    public ResponseEntity<?> addReservation(long id, Reservation reservation) {

        Optional<Venue> venueFromDb = venueRepository.findById(id);

        if (venueFromDb.isPresent()){
            venueFromDb.get().addReservation(reservation);
            return ResponseEntity.status(200).body("Reservation with id:" + reservation.getId() + ", successfully added");
        }

        return ResponseEntity.status(500).body("Venue with id " + id + " not found.");
    }
}
