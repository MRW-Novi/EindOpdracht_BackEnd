package nl.randomstuff.eindopdracht.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "venue")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "venue_name")
    private String venueName;

    @Column(name = "venue_email_address")
    private String venueEmailAddress;

    @Column(name = "startTime")
    private String startTime;

    @Column(name = "stopTime")
    private String stopTime;

    @Column(name = "slotDuration")
    private int slotDuration;

    @Column(name = "slotsPerDay")
    private int slotsPerDay;

    @Column(name = "peoplePerSlot")
    private int peoplePerSlot;

    @OneToMany(mappedBy = "venue")
    private List<Reservation> venueReservationList;

    @OneToOne(mappedBy = "venue")
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "venue", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getVenueEmailAddress() {
        return venueEmailAddress;
    }

    public void setVenueEmailAddress(String venueEmailAddress) {
        this.venueEmailAddress = venueEmailAddress;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
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
        this.venueReservationList = venueReservationList;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean addReservation(Reservation reservation) {

        if (reservationCheck(reservation)) {
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

        if (amountofPeeps + newReservation.getGroupSize() <= this.peoplePerSlot) {
            return true;
        }

        return false;
    }


}

