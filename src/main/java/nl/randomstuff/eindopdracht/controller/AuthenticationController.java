package nl.randomstuff.eindopdracht.controller;

import nl.randomstuff.eindopdracht.payload.request.LoginRequest;
import nl.randomstuff.eindopdracht.payload.request.SignUpRequest;
import nl.randomstuff.eindopdracht.payload.response.JwtResponse;
import nl.randomstuff.eindopdracht.payload.response.MessageResponse;
import nl.randomstuff.eindopdracht.service.AuthorizationService;
import nl.randomstuff.eindopdracht.service.UserDetailsServiceImpl;
import nl.randomstuff.eindopdracht.service.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthorizationService authorizationService;

    @GetMapping(value = "/authenticated")
    public ResponseEntity<Object> authenticated(Authentication authentication, Principal principal) {
        return ResponseEntity.ok().body(principal);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authorizationService.authenticateUser(loginRequest);
    }

    @PostMapping(value = "/signup/customer")
    public ResponseEntity<?> registerUserAsCustomer(@RequestBody SignUpRequest signUpRequest) {
        return authorizationService.registerUser(signUpRequest, "customer");
    }

    @PostMapping(value = "/signup/venue")
    public ResponseEntity<?> registerUserAsVenue(@RequestBody SignUpRequest signUpRequest) {
        return authorizationService.registerUser(signUpRequest, "venue");
    }



}
