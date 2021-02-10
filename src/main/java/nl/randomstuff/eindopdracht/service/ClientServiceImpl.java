package nl.randomstuff.eindopdracht.service;

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


//        if (clientRepository.existsById(id)) {
//            return clientRepository.findById(id).orElse(null);
//        } else {
//            throw new RecordNotFoundException();
//        }
    }

    @Override
    public ResponseEntity<?> deleteClient(long id) {

        Optional<Client> clientFromDb = clientRepository.findById(id);

        if (clientFromDb.isPresent()) {
            clientRepository.deleteById(id);
            return ResponseEntity.status(200).body("User with id " + id + " successfully deleted");
        }

        return ResponseEntity.status(500).body("User with id " + id + " not found.");

//        if (clientRepository.existsById(id)) {
//            clientRepository.deleteById(id);
//        } else {
//            throw new RecordNotFoundException();
//        }
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

//        if (clientRepository.existsById(id)) {
//            try {
//                Client existingClient = clientRepository.findById(id).orElse(null);
//                existingClient.setFirstName(client.getFirstName());
//                existingClient.setLastName(client.getLastName());
//                existingClient.setEmailAddress(client.getEmailAddress());
//                clientRepository.save(existingClient);
//            }
//            catch (Exception e){
//                throw new DatabaseErrorException();
//            }
//        }
//        else {
//            throw new RecordNotFoundException();
//        }
    }


    @Override
    public ResponseEntity<?> getClientReservationsByClientId(long id) {

        Optional<Client> clientFromDb = clientRepository.findById(id);

        if (clientFromDb.isPresent()) {
            return ResponseEntity.ok().body(clientFromDb.get().getClientReservationList());
        }


        return ResponseEntity.status(500).body("User with id " + id + " not found.");
    }
}
