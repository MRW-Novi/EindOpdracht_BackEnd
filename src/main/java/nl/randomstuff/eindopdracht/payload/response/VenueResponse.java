package nl.randomstuff.eindopdracht.payload.response;

import nl.randomstuff.eindopdracht.model.Reservation;

import java.util.List;

public class VenueResponse {

    private String name;

    private List<Reservation> venueReservationList;

    public List<Reservation> getVenueReservationList() {
        return venueReservationList;
    }

    public void setVenueReservationList(List<Reservation> venueReservationList) {
        this.venueReservationList = venueReservationList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
