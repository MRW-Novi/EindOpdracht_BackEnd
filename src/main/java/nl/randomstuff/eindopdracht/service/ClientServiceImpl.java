package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.exception.RecordNotFoundException;
import nl.randomstuff.eindopdracht.model.Client;
import nl.randomstuff.eindopdracht.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    VenueService venueService;

    @Override
    public ResponseEntity<?> getAllClients() {
        return ResponseEntity.status(200).body(clientRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getClientById(long id) {

        Optional<Client> clientFromDb = clientRepository.findById(id);

        if (clientFromDb.isPresent()) {
            return ResponseEntity.status(200).body(clientFromDb.get());
        }

        return ResponseEntity.status(500).body("User with id " + id + " not found.");

    }

    @Override
    public Client getClientEntity(long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return client.get();
        }
        throw new RecordNotFoundException(Long.toString(id));
    }

    @Override
    public ResponseEntity<?> deleteClient(long id) {

        Optional<Client> clientFromDb = clientRepository.findById(id);

        if (clientFromDb.isPresent()) {
            clientRepository.deleteById(id);
            return ResponseEntity.status(200).body("User with id " + id + " successfully deleted");
        }

        return ResponseEntity.status(500).body("User with id " + id + " not found.");

    }

    @Override
    public ResponseEntity<?> saveClient(Client client) {
        Client newClient = clientRepository.save(client);
        return ResponseEntity.status(200).body("user with id " + newClient.getId() + " successfully created");
    }

    @Override
    public ResponseEntity<?> updateClient(long id, Client client) {

        Optional<Client> clientFromDb = clientRepository.findById(id);

        if (clientFromDb.isPresent()) {
            clientFromDb.get().setFirstName(client.getFirstName());
            clientFromDb.get().setLastName(client.getLastName());
            return ResponseEntity.status(200).body("User with id " + id + " updated");
        } else {
            return ResponseEntity.status(500).body("User with id " + id + " not found.");
        }

    }


    @Override
    public ResponseEntity<?> getClientReservationsByClientId(long id) {

        Optional<Client> clientFromDb = clientRepository.findById(id);

        if (clientFromDb.isPresent()) {
            return ResponseEntity.ok().body(clientFromDb.get().getClientReservationList());
        }


        return ResponseEntity.status(500).body("User with id " + id + " not found.");
    }

    @Override
    public ResponseEntity<?> getVenueAvailabilityForClient(long id) {
        return venueService.getVenueAvailability(id);
    }
}
