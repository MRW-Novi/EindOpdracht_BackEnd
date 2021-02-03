package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.model.Client;
import org.springframework.http.ResponseEntity;

public interface ClientService {

    ResponseEntity<?> getAllClients();

    ResponseEntity<?> getClientById(long id);

    ResponseEntity<?> deleteClient(long id);

    ResponseEntity<?> saveClient(Client client);

    ResponseEntity<?> updateClient(long id, Client client);

    ResponseEntity<?> getClientReservationsByClientId(long id);
}
