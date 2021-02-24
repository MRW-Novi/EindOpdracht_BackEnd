package nl.randomstuff.eindopdracht.payload.response;

import nl.randomstuff.eindopdracht.model.Reservation;

import java.util.List;

public class ReservationListResponse {

    private List<Reservation> reservations;

    public ReservationListResponse(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
