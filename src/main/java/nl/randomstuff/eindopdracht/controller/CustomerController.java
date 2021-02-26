package nl.randomstuff.eindopdracht.controller;

import nl.randomstuff.eindopdracht.model.Customer;
import nl.randomstuff.eindopdracht.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/customer")
@RestController
//@CrossOrigin(origins = "*")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/data")
    public ResponseEntity<?> getCustomerData(@RequestHeader("Authorization") String token) {
        return customerService.getCustomerDataResponse(token);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteCustomer(@RequestHeader("Authorization") String bearerToken) {
        return customerService.deleteCustomer(bearerToken);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateCustomer(@RequestHeader("Authorization") String bearerToken, @RequestBody Customer customer) {
        return customerService.updateCustomer(bearerToken, customer);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/reservations")
    public ResponseEntity<?> getCustomerReservationsByUserId(@RequestHeader("Authorization") String bearerToken) {
        return customerService.getCustomerReservationsByUserId(bearerToken);
    }


}
