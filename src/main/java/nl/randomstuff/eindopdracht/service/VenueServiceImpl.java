package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.exception.RecordNotFoundException;
import nl.randomstuff.eindopdracht.model.*;
import nl.randomstuff.eindopdracht.payload.request.ReservationRequest;
import nl.randomstuff.eindopdracht.payload.response.ReservationListResponse;
import nl.randomstuff.eindopdracht.payload.response.VenueListResponse;
import nl.randomstuff.eindopdracht.payload.response.VenueResponse;
import nl.randomstuff.eindopdracht.repository.UserRepository;
import nl.randomstuff.eindopdracht.repository.VenueRepository;
import nl.randomstuff.eindopdracht.service.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VenueServiceImpl implements VenueService {

    UserRepository userRepository;
    VenueRepository venueRepository;
    CustomerService customerService;
    JwtUtil jwtUtil;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setVenueRepository(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ResponseEntity<?> getAllVenues() {
        VenueListResponse venueListResponse = new VenueListResponse();
        List<Venue> venueList = venueRepository.findAll();
        venueListResponse.setVenues(venueList);
        return ResponseEntity.status(200).body(venueListResponse);
    }

    @Override
    public ResponseEntity<?> getVenueDataResponse(long id) {

        Venue venue = getVenueEntityThroughVenueId(id);

        VenueResponse venueResponse = new VenueResponse();
        venueResponse.setId(venue.getId());
        venueResponse.setName(venue.getVenueName());

        return ResponseEntity.status(200).body(
                venueResponse
        );

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
        return ResponseEntity.status(200).body(newVenue);
    }

    @Override
    public ResponseEntity<?> updateVenue(String bearerToken, Venue venue) {

        String jwtString = jwtUtil.internalParseJwt(bearerToken);
        String id = jwtUtil.getUsernameFromJwtToken(jwtString);

        Venue venueEntityThroughUserId = getVenueEntityThroughUserId(id);

        venueEntityThroughUserId.setVenueName(venue.getVenueName());
        venueEntityThroughUserId.setVenueEmailAddress(venue.getVenueEmailAddress());

        if (venueEntityThroughUserId.getAddress() == null) {
            venueEntityThroughUserId.setAddress(new Address());
        }
        Address venueAddress = venueEntityThroughUserId.getAddress();
        Address newAddress = venue.getAddress();
        venueAddress.setVenue(venueEntityThroughUserId);
        venueAddress.setCity(newAddress.getCity());
        venueAddress.setStreetName(newAddress.getStreetName());
        venueAddress.setNumber(newAddress.getNumber());

        Venue newVenue = venueRepository.save(venueEntityThroughUserId);

//        return ResponseEntity.status(200).body("Venue data of user " + id + " updated");
        return ResponseEntity.status(200).body(newVenue);


    }

    @Override
    public Venue getVenueEntityThroughUserId(String id) {

        Optional<User> user = userRepository.findById(id);
        Venue venueFromDb;

        if (user.isPresent()) {
            return venueFromDb = user.get().getVenue();
        }
        throw new UsernameNotFoundException(id);
    }

    @Override
    public Venue getVenueEntityThroughVenueId(long id) {

        Optional<Venue> venue = venueRepository.findById(id);

        if (venue.isPresent()) {
            return venue.get();
        }
        throw new RecordNotFoundException("" + id);
    }

    @Override
    public ResponseEntity<?> addReservation(ReservationRequest reservationRequest) {

        Reservation reservation = new Reservation();

        System.out.println(reservationRequest.getVenueId());
        Venue venue = getVenueEntityThroughVenueId(reservationRequest.getVenueId());
        System.out.println(venue);

        System.out.println(reservationRequest.getCustomerId());
        Customer customer = customerService.getCustomerEntityThroughCustomerId(reservationRequest.getCustomerId());
        System.out.println(customer);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        reservation.setDate(LocalDate.parse(reservationRequest.getDate(), dateFormatter));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm");
        reservation.setDate(LocalDate.parse(reservationRequest.getTime(), timeFormatter));

        reservation.setGroupSize(reservationRequest.getGroupSize());
        reservation.setTimeSlotIndex(reservationRequest.getTimeSlotIndex());

        reservation.setVenue(venue);
        reservation.setCustomer(customer);

        venue.addReservation(reservation);
        customer.addReservation(reservation);

        return ResponseEntity.ok().body("added reservation:" + reservation.getId() + "to customer" + customer.getFirstName() + "and venue" + venue.getVenueName());
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

        Venue venue = getVenueEntityThroughVenueId(id);

        List<Reservation> reservations = venue
                .getVenueReservationList()
                .stream()
                .filter(reservation -> reservation.getDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new ReservationListResponse(reservations));
    }
}
