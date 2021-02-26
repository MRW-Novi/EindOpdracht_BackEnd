package nl.randomstuff.eindopdracht.service;


import nl.randomstuff.eindopdracht.model.Customer;
import nl.randomstuff.eindopdracht.model.ERole;
import nl.randomstuff.eindopdracht.model.Role;
import nl.randomstuff.eindopdracht.model.User;
import nl.randomstuff.eindopdracht.model.Venue;
import nl.randomstuff.eindopdracht.payload.request.LoginRequest;
import nl.randomstuff.eindopdracht.payload.request.SignUpRequest;
import nl.randomstuff.eindopdracht.payload.response.JwtResponse;
import nl.randomstuff.eindopdracht.payload.response.MessageResponse;
import nl.randomstuff.eindopdracht.repository.RoleRepository;
import nl.randomstuff.eindopdracht.repository.UserRepository;
import nl.randomstuff.eindopdracht.service.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
public class AuthorizationService {

    private static final String ROLE_NOT_FOUND_ERROR = "Error: Role is not found.";

    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtils;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEncoder(PasswordEncoder passwordEncoder) {
        this.encoder = passwordEncoder;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtUtils(JwtUtil jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * Deze methode verwerkt de gebruiker die wil registreren. De username en e-mail worden gecheckt. Eventuele rollen
     * worden toegevoegd en de gebruiker wordt opgeslagen in de database.
     *
     * @param signUpRequest de payload signup-request met gebruikersnaam en wachtwoord.
     * @return een HTTP response met daarin een succesbericht.
     */
    public ResponseEntity<?> registerUser(@Valid SignUpRequest signUpRequest, String role) {

        if (Boolean.TRUE.equals(userRepository.existsById(signUpRequest.getUsername()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );

        Set<Role> roles = new HashSet<>();

        switch (role) {
            case "admin":
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_ERROR));
                roles.add(adminRole);
                break;

            case "venue":
                Role venueRole = roleRepository.findByName(ERole.ROLE_VENUE)
                        .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_ERROR));
                roles.add(venueRole);
                user.setVenue(new Venue());
                break;

            case "customer":
                Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                        .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_ERROR));
                roles.add(customerRole);
                user.setCustomer(new Customer());
                break;

            default:
                throw new RuntimeException("Provided role empty");
        }



        user.setRoles(roles);
        User savedUser = userRepository.save(user);

//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        return ResponseEntity.ok().body(savedUser);
    }

    /**
     * Deze methode controleert de ontvangen username en wachtwoord. Het gebruikt hiervoor de
     * AuthenticationManager. I.a.w. Spring security doet die allemaal voor ons.
     * <p>
     * Wanneer de gebruikersnaam/wachtwoord combinatie niet klopt, wordt er een Runtime exception gegooid:
     * 401 Unauthorized. Deze wordt gegooid door
     * {@link nl.randomstuff.eindopdracht.service.security.jwt.AuthEntryPointJwt}
     *
     * @param loginRequest De payload met username en password.
     * @return een HTTP-response met daarin de JWT-token.
     */
    public ResponseEntity<JwtResponse> authenticateUser(@Valid LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));
        } catch(Exception e) {
            throw new RuntimeException("empty username or password");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
//                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

}
