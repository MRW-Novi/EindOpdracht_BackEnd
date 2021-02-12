package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.exception.RecordNotFoundException;
import nl.randomstuff.eindopdracht.model.Reservation;
import nl.randomstuff.eindopdracht.model.Venue;
import nl.randomstuff.eindopdracht.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if (venueFromDb.isPresent()) {
            return ResponseEntity.status(200).body(venueRepository.findById(id));
        }
        return ResponseEntity.status(500).body("Venue with id " + id + " not found.");

    }

    @Override
    public ResponseEntity<?> deleteVenue(long id) {
        Optional<Venue> venueFromDb = venueRepository.findById(id);

        if (venueFromDb.isPresent()) {
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
            venueFromDb.get().setAddress(venue.getAddress());
            return ResponseEntity.status(200).body("Venue with id " + id + " updated");
        }
        return ResponseEntity.status(500).body("Venue with id " + id + " not found.");
    }

    @Override
    public Venue getVenueEntity(long id) {
        Optional<Venue> venue = venueRepository.findById(id);
        if (venue.isPresent()) {
            return venue.get();
        }
        throw new RecordNotFoundException(Long.toString(id));
    }

    @Override
    public ResponseEntity<?> addReservation(long id, Reservation reservation) {

        Optional<Venue> venueFromDb = venueRepository.findById(id);

        if (venueFromDb.isPresent()) {
            if (venueFromDb.get().addReservation(reservation)) {
                return ResponseEntity.status(200).body("Reservation with id:" + reservation.getId() + ", successfully added");
            }
        }

        return ResponseEntity.status(500).body("Venue with id " + id + " not found.");
    }

    @Override
    public ResponseEntity<?> getVenueReservationsByVenueId(long id) {

        Optional<Venue> venue = venueRepository.findById(id);

        if (venue.isPresent()) {
            return (ResponseEntity.ok().body(venue.get().getVenueReservationList()));
        }
        return ResponseEntity.status(500).body("Venue with id " + id + " not found.");
    }

    @Override
    public ResponseEntity<?> getVenueAvailability(long id) {

        Venue venue = getVenueEntity(id);

            return (ResponseEntity.ok().body(
                    venue
                    .getVenueReservationList()
                    .stream()
                    .filter(reservation -> reservation.getDate().isAfter(LocalDate.now()))
                    .collect(Collectors.toList())
            ));
        }
}
