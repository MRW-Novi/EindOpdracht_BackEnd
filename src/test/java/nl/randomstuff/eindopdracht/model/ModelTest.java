package nl.randomstuff.eindopdracht.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelTest {

    private Client client;
    private Venue venue;
    private Reservation reservation;
    private Address address;

    @BeforeEach()
    public void fillClient(){// TODO: Doet t niet

        address = new Address();
        address.setCity("Agga");
        address.setNumber("420");
        address.setStreetName("Baggerlaan");

        venue = new Venue();
        venue.setVenueName("Sportschool Pain&Gain");
        venue.setAddress(address);
        venue.setPeoplePerSlot(30);
        venue.setSlotDuration(90);

        client = new Client();
        client.setFirstName("Henk");
        client.setLastName("deTank");

        reservation = new Reservation();
        reservation.setDate(LocalDate.now());
        reservation.setTime(LocalTime.NOON);
        reservation.setGroupSize(4);
        reservation.setTimeSlotIndex(2);

    }

    @Test
    public void addAndGetReservationThroughClient(){

        client.addReservation(reservation);

        assertThat(reservation).isEqualTo(client.getClientReservationList().get(0));
    }

    @Test
    public void addAndGetReservationThroughVenue(){

        venue.addReservation(reservation);

        assertThat(reservation).isEqualTo(venue.getVenueReservationList().get(0));
    }

    @Test
    public void getClientThroughReservationFromVenue(){

        reservation.setClient(client);
        venue.addReservation(reservation);

        assertThat(client).isEqualTo(venue.getVenueReservationList().get(0).getClient());
    }
}
