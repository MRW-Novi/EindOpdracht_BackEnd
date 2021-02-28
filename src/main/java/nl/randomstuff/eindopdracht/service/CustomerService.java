package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface CustomerService {

    Customer saveCustomerInDb(Customer customer);

    ResponseEntity<?> getAllCustomers();

    Customer getCustomerEntityThroughUserId(String id);

    Customer getCustomerEntityThroughCustomerId(long id);

    ResponseEntity<?> getCustomerDataResponse(String id);

    ResponseEntity<?> deleteCustomer(String bearerToken);

    ResponseEntity<?> updateCustomer(String id, Customer customer);

    ResponseEntity<?> getCustomerReservationsByUserId(String id);

    ResponseEntity<?> uploadImage(String bearerToken, MultipartFile image) throws Exception;

    ResponseEntity<?> downloadImage(String bearerToken);
}
