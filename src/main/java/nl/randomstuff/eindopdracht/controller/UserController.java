package nl.randomstuff.eindopdracht.controller;

import nl.randomstuff.eindopdracht.model.User;
import nl.randomstuff.eindopdracht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/data")//TODO: Updating user increments or creates new child entity (customer/venue)
    public ResponseEntity<?> getUserById(@RequestHeader("Authorization") String bearerToken){
        return userService.getUserResponse(bearerToken);
    }

    @DeleteMapping(value = "/{username}")//TODO: secure/hide this function behind bearerToken
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username){
        return userService.deleteUser(username);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String bearerToken, @RequestBody User user){
        return userService.updateUser(bearerToken, user);
    }

    //TODO: how to make logout feature? force dump bearer token? handle in frontend?


}
