package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.model.ERole;
import nl.randomstuff.eindopdracht.model.Role;
import nl.randomstuff.eindopdracht.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Set;


public interface UserService {

//    ResponseEntity<?> createUserAsCustomer(User user);
//
//    ResponseEntity<?> createUserAsVenue(User user);

    ResponseEntity<?> updateUser(String bearerToken, User user);

    ResponseEntity<?> deleteUser(String username);

    ResponseEntity<?> getUsers();

    ResponseEntity<?> getUserResponse(String username);

    User getUserEntity(String username);

    ResponseEntity<?> saveUser(User user);

    Set<Role> getAuthorities(String username);

    void addAuthority(String username, ERole role);

}
