package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.model.ERole;
import nl.randomstuff.eindopdracht.model.Role;
import nl.randomstuff.eindopdracht.model.User;
import nl.randomstuff.eindopdracht.repository.UserRepository;
import nl.randomstuff.eindopdracht.service.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private PasswordEncoder encoder;
    private UserRepository userRepository;
    private JwtUtil jwtUtil;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEncoder(PasswordEncoder passwordEncoder) {
        this.encoder = passwordEncoder;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ResponseEntity<?> updateUser(String bearerToken, User newUser) {

        String jwtString = jwtUtil.internalParseJwt(bearerToken);
        String id = jwtUtil.getUsernameFromJwtToken(jwtString);

        User currentUser = getUserEntity(id);

        currentUser.setEmail(newUser.getEmail());
        currentUser.setPassword(encoder.encode(newUser.getPassword()));
        User updatedUser = userRepository.save(currentUser);
        return ResponseEntity.status(200).body(updatedUser);

    }

    @Override
    public ResponseEntity<?> deleteUser(String bearerToken) {

        String jwtString = jwtUtil.internalParseJwt(bearerToken);
        String username = jwtUtil.getUsernameFromJwtToken(jwtString);

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
    public ResponseEntity<?> getUserResponse(String bearerToken) {
        String jwtString = jwtUtil.internalParseJwt(bearerToken);
        String id = jwtUtil.getUsernameFromJwtToken(jwtString);
        return ResponseEntity.status(200).body(getUserEntity(id));
    }

    @Override
    public User getUserEntity(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException(username);
    }

    @Override
    public ResponseEntity<?> saveUser(User user) {
        userRepository.save(user);
        return ResponseEntity.status(200).body(user);
    }

    @Override
    public Set<Role> getAuthorities(String username) {
//        return ResponseEntity.status(200).body(getUserEntity(username).getAuthorities());
        return getUserEntity(username).getRoles();
    }

    @Override
    public void addAuthority(String username, ERole role) {
        User user = getUserEntity(username);
//        user.addAuthority(new Authority(username, authority));
        Role newRole = new Role(role);
        user.getRoles().add(newRole);
        userRepository.save(user);
    }


}
