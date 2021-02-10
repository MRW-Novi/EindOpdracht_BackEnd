package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.model.Authority;
import nl.randomstuff.eindopdracht.model.User;
import nl.randomstuff.eindopdracht.repository.UserRepository;
import nl.randomstuff.eindopdracht.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> createUser(User user) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        user.setApikey(randomString);
        User newUser = userRepository.save(user);
        return ResponseEntity.status(200).body(newUser.getUsername());
    }

    @Override
    public ResponseEntity<?> updateUser(String username, User newUser) {

        Optional<User> currentUser = userRepository.findById(username);

        if (currentUser.isPresent()) {
            currentUser.get().setEmail(newUser.getEmail());
            currentUser.get().setPassword(newUser.getPassword());
            return ResponseEntity.status(200).body("User " + username + " updated");
        }
        return ResponseEntity.status(500).body("user " + username + " not found");
    }

    @Override
    public ResponseEntity<?> deleteUser(String username) {

        Optional<User> userFromDb = userRepository.findById(username);

        if (userFromDb.isPresent()) {
            userRepository.deleteById(username);
            return ResponseEntity.status(200).body("user " + username + " deleted");
        }

        return ResponseEntity.status(500).body("user " + username + " not found");
    }

    @Override
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.status(200).body(userRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getUserResponse(String username) {
        return ResponseEntity.status(200).body(getUserData(username));
    }

    @Override
    public User getUserData(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException(username);
    }

    @Override
    public ResponseEntity<?> getAuthorities(String username) {
        return ResponseEntity.status(200).body(getUserData(username).getAuthorities());
    }

    @Override
    public void addAuthority(String username, String authority) {
        User user = getUserData(username);
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    @Override
    public void removeAuthority(String username, String authority) {
        User user = getUserData(username);
        Authority authorityToRemove = user
                .getAuthorities()
                .stream()
                .filter(a -> a.getAuthority().equalsIgnoreCase(authority))
                .findAny()
                .get();//TODO: hoe checken? .isPresent is non-applicable

        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }
}
