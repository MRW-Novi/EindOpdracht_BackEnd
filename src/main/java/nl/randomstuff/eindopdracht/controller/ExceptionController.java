package nl.randomstuff.eindopdracht.controller;

import nl.randomstuff.eindopdracht.exception.DatabaseErrorException;
import nl.randomstuff.eindopdracht.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException exception) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DatabaseErrorException.class)
    public ResponseEntity<Object> exception(DatabaseErrorException exception) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
