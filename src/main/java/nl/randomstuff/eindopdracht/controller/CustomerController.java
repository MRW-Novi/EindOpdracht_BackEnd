package nl.randomstuff.eindopdracht.controller;

import nl.randomstuff.eindopdracht.model.Customer;
import nl.randomstuff.eindopdracht.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/customer")
@RestController
//@CrossOrigin(origins = "*")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/data")
    public ResponseEntity<?> getCustomerData(@RequestHeader("Authorization") String token) {
        return customerService.getCustomerDataResponse(token);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") long id) {
        return customerService.deleteCustomer(id);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateCustomer(@RequestHeader("Authorization") String bearerToken, @RequestBody Customer customer) {
        return customerService.updateCustomer(bearerToken, customer);
    }

    @GetMapping(value = "/{id}/reservations")
    public ResponseEntity<?> getCustomerReservationsByUserId(@PathVariable("id") String id) {
        return customerService.getCustomerReservationsByUserId(id);
    }


}
