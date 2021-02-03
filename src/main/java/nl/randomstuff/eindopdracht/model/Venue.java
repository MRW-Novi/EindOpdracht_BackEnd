package nl.randomstuff.eindopdracht.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Entity
@Table(name = "venue")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "venue_name")
    private String venueName;

    @Column(name = "location")
    private String venueLocation;

    @Column(name = "venue_email_address")
    private String venueEmailAddress;

    @Column(name = "startTime")
    private LocalTime startTime;

    @Column(name = "stopTime")
    private LocalTime stopTime;

    @Column(name = "slotDuration")
    private int slotDuration;

    @Column(name = "slotsPerDay")
    private int slotsPerDay;

    @Column(name = "peoplePerSlot")
    private int peoplePerSlot;

    @OneToMany(mappedBy = "venue")
    private List<Reservation> venueReservationList;

    public long getId() {
        return id;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueLocation() {
        return venueLocation;
    }

    public void setVenueLocation(String venueLocation) {
        this.venueLocation = venueLocation;
    }

    public String getVenueEmailAddress() {
        return venueEmailAddress;
    }

    public void setVenueEmailAddress(String venueEmailAddress) {
        this.venueEmailAddress = venueEmailAddress;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalTime stopTime) {
        this.stopTime = stopTime;
    }

    public int getSlotDuration() {
        return slotDuration;
    }

    public void setSlotDuration(int slotDuration) {
        this.slotDuration = slotDuration;
    }

    public List<Reservation> getVenueReservationList() {
        return venueReservationList;
    }

    public void setVenueReservationList(List<Reservation> venueReservationList) {
        this.venueReservationList = venueReservationList; //TODO: toevoegen aan deze lijst, altijd eerst getten, dan aan de lijst toevoegen, dan weer setten?
    }

    public int getSlotsPerDay() {
        return slotsPerDay;
    }

    public void setSlotsPerDay(int slotsPerDay) {
        this.slotsPerDay = slotsPerDay;
    }

    public int getPeoplePerSlot() {
        return peoplePerSlot;
    }

    public void setPeoplePerSlot(int peoplePerSlot) {
        this.peoplePerSlot = peoplePerSlot;
    }

    public boolean addReservation(Reservation reservation ) {

        if(reservationCheck(reservation)) {
            venueReservationList.add(reservation);
            return true;
        }
        return false;
    }

    private boolean reservationCheck(Reservation newReservation) {
        List<Reservation> filteredReservations =
                venueReservationList.stream()
                .filter(c -> c.getDate() == newReservation.getDate())
                .filter(c -> c.getTimeSlotIndex() == newReservation.getTimeSlotIndex())
                .collect(Collectors.toList());


        int amountofPeeps = 0;

        for (Reservation r : filteredReservations) {
            amountofPeeps += r.getGroupSize();
        }

        if(amountofPeeps + newReservation.getGroupSize() <= this.peoplePerSlot) {
            return true;
        }

        return false;
    }

    //0.
    //1. filter uit lijst alles afhankelijk van datum
    //2. tel personen op van gefilterde lijst
}

