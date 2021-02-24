package nl.randomstuff.eindopdracht.payload.response;

import nl.randomstuff.eindopdracht.model.Venue;

import java.util.List;

public class VenueListResponse {

    private List<Venue> venues;

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }
}
