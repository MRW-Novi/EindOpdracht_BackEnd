package nl.randomstuff.eindopdracht.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
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

    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<Authority> authorities = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "user_client",
            joinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "username")},
            inverseJoinColumns =
                    {@JoinColumn(name = "client_id", referencedColumnName = "id")})
    private Client client; //TODO: waarom hier geen null? (https://www.baeldung.com/jpa-one-to-one)

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "user_venue",
            joinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "username")},
            inverseJoinColumns =
                    {@JoinColumn(name = "venue_id", referencedColumnName = "id")})
    private Venue venue;


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

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authorityToRemove) {
        this.authorities.remove(authorityToRemove);
    }
}
