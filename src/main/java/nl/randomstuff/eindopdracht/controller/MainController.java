package nl.randomstuff.eindopdracht.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping(value="/")
    public ResponseEntity<Object> sayHello() {
        return new ResponseEntity<>("You found the backend server, congratulations!", HttpStatus.OK);
    }
}
