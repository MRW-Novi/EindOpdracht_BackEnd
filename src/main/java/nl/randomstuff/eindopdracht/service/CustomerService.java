package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.model.Customer;
import org.springframework.http.ResponseEntity;


public interface CustomerService {

    Customer saveCustomerInDb(Customer customer);

    ResponseEntity<?> getAllCustomers();

    Customer getCustomerEntityThroughUserId(String id);

    Customer getCustomerEntityThroughCustomerId(long id);

    ResponseEntity<?> getCustomerDataResponse(String id);

    ResponseEntity<?> deleteCustomer(String bearerToken);

    ResponseEntity<?> updateCustomer(String id, Customer customer);

    ResponseEntity<?> getCustomerReservationsByUserId(String id);



}
