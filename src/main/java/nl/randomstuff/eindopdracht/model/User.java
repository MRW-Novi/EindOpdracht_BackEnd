package nl.randomstuff.eindopdracht.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {


    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column
    private String apikey;

    @Column
    private String email;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_username"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "user_customer",
            joinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "username")},
            inverseJoinColumns =
                    {@JoinColumn(name = "customer_id", referencedColumnName = "id")})
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "user_venue",
            joinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "username")},
            inverseJoinColumns =
                    {@JoinColumn(name = "venue_id", referencedColumnName = "id")})
    private Venue venue;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
