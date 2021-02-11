package nl.randomstuff.eindopdracht.controller;

import nl.randomstuff.eindopdracht.model.User;
import nl.randomstuff.eindopdracht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/all")
    public ResponseEntity<?> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserById(@PathVariable("username") String username){
        return userService.getUserResponse(username);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username){
        return userService.deleteUser(username);
    }

    @PostMapping(value = "/as_client")
    public ResponseEntity<?> createUserAsClient(@RequestBody User user) {
        return userService.createUserAsClient(user);
    }

    @PostMapping(value = "/as_venue")
    public ResponseEntity<?> createUserAsVenue(@RequestBody User user) {
        return userService.createUserAsVenue(user);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<?> updateUser(@PathVariable("username") String username, @RequestBody User user){
        return userService.updateUser(username, user);
    }


}
