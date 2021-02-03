package nl.randomstuff.eindopdracht.controller;

import nl.randomstuff.eindopdracht.model.Client;
import nl.randomstuff.eindopdracht.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public ResponseEntity<?> getClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<?> getClient(@PathVariable("id") long id) {
        return clientService.getClientById(id);
    }

    @DeleteMapping(value = "/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") long id) {
        return clientService.deleteClient(id);
    }

    @PostMapping(value = "/clients")
    public ResponseEntity<?> saveClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @PutMapping(value = "/clients/{id}")
    public ResponseEntity<?> updateClient(@PathVariable("id") long id, @RequestBody Client client) {
        return clientService.updateClient(id, client);
    }

    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<?> getReservationsByClientId(@PathVariable("id") long id) {
        return clientService.getClientReservationsByClientId(id);
    }

//    @GetMapping(value = "/clients/{id}")
//    public ResponseEntity<Object> getClientReservations(@PathVariable("id") long id) {
//        List<Reservation> reservations = clientService.getClientReservations(id);
//        return new ResponseEntity<>(reservations, HttpStatus.OK);
//    }
}
