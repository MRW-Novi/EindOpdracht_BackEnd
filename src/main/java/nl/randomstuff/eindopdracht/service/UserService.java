package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createUser(User user);

    ResponseEntity<?> updateUser(String username, User user);

    ResponseEntity<?> deleteUser(String username);

    ResponseEntity<?> getUsers();

    ResponseEntity<?> getUserResponse(String username);

    User getUserData(String username);

    ResponseEntity<?> getAuthorities(String username);

    void addAuthority(String username, String authority);

    void removeAuthority(String username, String authority);
}
