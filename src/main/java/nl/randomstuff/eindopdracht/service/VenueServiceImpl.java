package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.exception.RecordNotFoundException;
import nl.randomstuff.eindopdracht.model.Address;
import nl.randomstuff.eindopdracht.model.Customer;
import nl.randomstuff.eindopdracht.model.Reservation;
import nl.randomstuff.eindopdracht.model.User;
import nl.randomstuff.eindopdracht.model.Venue;
import nl.randomstuff.eindopdracht.payload.request.ReservationRequest;
import nl.randomstuff.eindopdracht.payload.response.ReservationListResponse;
import nl.randomstuff.eindopdracht.payload.response.VenueListResponse;
import nl.randomstuff.eindopdracht.payload.response.VenueResponse;
import nl.randomstuff.eindopdracht.repository.ReservationRepository;
import nl.randomstuff.eindopdracht.repository.UserRepository;
import nl.randomstuff.eindopdracht.repository.VenueRepository;
import nl.randomstuff.eindopdracht.service.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VenueServiceImpl implements VenueService {

    UserRepository userRepository;
    VenueRepository venueRepository;
    ReservationRepository reservationRepository;
    CustomerService customerService;
    UserService userService;
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
    public void setReservationRepository(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ResponseEntity<?> getAllVenues() {
        VenueListResponse venueListResponse = new VenueListResponse();
        List<Venue> venueList = venueRepository.findAll();
        List<Venue> shortVenueList = new ArrayList<>();
        for (Venue venue : venueList) {
            Venue shortVenue = new Venue();
            shortVenue.setId(venue.getId());
            shortVenue.setAddress(venue.getAddress());
            shortVenue.setVenueName(venue.getVenueName());
            shortVenue.setVenueEmailAddress(venue.getVenueEmailAddress());
            shortVenueList.add(shortVenue);
        }
        venueListResponse.setVenues(shortVenueList);
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
    public ResponseEntity<?> deleteVenue(String bearerToken) {
        String jwtString = jwtUtil.internalParseJwt(bearerToken);
        String username = jwtUtil.getUsernameFromJwtToken(jwtString);
        User relatedUser = userService.getUserEntity(username);
        long id = relatedUser.getVenue().getId();

        Optional<Venue> venueFromDb = venueRepository.findById(id);

        if (venueFromDb.isPresent()) {
            relatedUser.setVenue(null);
            userService.saveUser(relatedUser);
            venueRepository.deleteById(id);
            return ResponseEntity.status(200).body("Venue with id " + id + " successfully deleted");
        }
        return ResponseEntity.status(500).body("Venue with id " + id + " not found.");

    }

    @Override
    public Venue saveVenueInDb(Venue venue) {
        return venueRepository.save(venue);
    }

    @Override
    public ResponseEntity<?> updateVenue(String bearerToken, Venue venue) {

        String jwtString = jwtUtil.internalParseJwt(bearerToken);
        String id = jwtUtil.getUsernameFromJwtToken(jwtString);

        Venue venueEntityThroughUserId = getVenueEntityThroughUserId(id);

        venueEntityThroughUserId.setVenueName(venue.getVenueName());
        venueEntityThroughUserId.setVenueEmailAddress(venue.getVenueEmailAddress());
        venueEntityThroughUserId.setPeoplePerSlot(venue.getPeoplePerSlot());
        venueEntityThroughUserId.setSlotDuration(venue.getSlotDuration());
        venueEntityThroughUserId.setStartTime(venue.getStartTime());
        venueEntityThroughUserId.setStopTime(venue.getStopTime());
        venueEntityThroughUserId.setSlotsPerDay(venue.getSlotsPerDay());

        if (venueEntityThroughUserId.getVenueReservationList() == null) {
            venueEntityThroughUserId.setVenueReservationList(new ArrayList<>());
        }

        List<Reservation> reservations = new ArrayList<>();

        if (venueEntityThroughUserId.getAddress() == null) {
            venueEntityThroughUserId.setAddress(new Address());
        }
        Address venueAddress = venueEntityThroughUserId.getAddress();
        Address newAddress = venue.getAddress();
        venueAddress.setVenue(venueEntityThroughUserId);
        venueAddress.setCity(newAddress.getCity());
        venueAddress.setStreetName(newAddress.getStreetName());
        venueAddress.setNumber(newAddress.getNumber());

        Venue newVenue = saveVenueInDb(venueEntityThroughUserId);

//        return ResponseEntity.status(200).body("Venue data of user " + id + " updated");
        return ResponseEntity.status(200).body(newVenue);


    }

    @Override
    public Venue getVenueEntityThroughUserId(String id) {
        return userService.getUserEntity(id).getVenue();
    }

    @Override
    public Venue getVenueEntityThroughVenueId(long id) {

        Optional<Venue> venue = venueRepository.findById(id);

        if (venue.isPresent()) {
            return venue.get();
        }
        throw new RecordNotFoundException("cant find venue with id: " + id);
    }

    @Override
    public ResponseEntity<?> addReservation(ReservationRequest reservationRequest) {

        Reservation reservation = new Reservation();

        Venue venue = getVenueEntityThroughVenueId(reservationRequest.getVenueId());

        Customer customer = customerService.getCustomerEntityThroughCustomerId(reservationRequest.getCustomerId());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        reservation.setDate(LocalDate.parse(reservationRequest.getDate(), dateFormatter));
//        System.out.println(reservation.getDate());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        reservation.setTime(LocalTime.parse(reservationRequest.getTime(), timeFormatter));
//        System.out.println(reservation.getTime());

        reservation.setGroupSize(reservationRequest.getGroupSize());
        reservation.setTimeSlotIndex(reservationRequest.getTimeSlotIndex());

        reservation.setVenue(venue);
        reservation.setCustomer(customer);

        if (venue.addReservation(reservation)) {

            customer.addReservation(reservation);

//            return ResponseEntity.ok().body("added reservation:" + reservation.getId() + "to customer" + customer.getFirstName() + "and venue" + venue.getVenueName());

//            Customer savedCustomer = customerService.saveCustomerInDb(customer);
//            Venue savedVenue = saveVenueInDb(venue);
            Reservation savedReservation = reservationRepository.save(reservation);

            return ResponseEntity.status(200).body(savedReservation);
        } else {
            return ResponseEntity.status(500).body("This reservation exceeds maximum allowed 'peeps'");
        }
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
