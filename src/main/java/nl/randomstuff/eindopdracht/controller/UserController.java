package nl.randomstuff.eindopdracht.controller;

import nl.randomstuff.eindopdracht.model.User;
import nl.randomstuff.eindopdracht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/user")
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getUsers(){
        return userService.getUsers();
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('VENUE') or hasRole('ADMIN')")
    @GetMapping("/data")
    public ResponseEntity<?> getUserById(@RequestHeader("Authorization") String bearerToken){
        return userService.getUserResponse(bearerToken);
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('VENUE') or hasRole('ADMIN')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String bearerToken){
        return userService.deleteUser(bearerToken);
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('VENUE') or hasRole('ADMIN')")
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String bearerToken, @RequestBody User user){
        return userService.updateUser(bearerToken, user);
    }

}
