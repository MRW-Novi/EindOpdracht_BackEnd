package nl.randomstuff.eindopdracht.service;

import javassist.tools.web.BadHttpRequest;
import nl.randomstuff.eindopdracht.exception.RecordNotFoundException;
import nl.randomstuff.eindopdracht.model.Customer;
import nl.randomstuff.eindopdracht.repository.CustomerRepository;
import nl.randomstuff.eindopdracht.service.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String TOKEN_TYPE = "Bearer ";

    private CustomerRepository customerRepository;
    private UserService userService;
    private VenueService venueService;
    private JwtUtil jwtUtil;

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setVenueService(VenueService venueService) {
        this.venueService = venueService;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ResponseEntity<?> getAllCustomers() {
        return ResponseEntity.status(200).body(customerRepository.findAll());
    }

    @Override
    public Customer getCustomerEntityThroughUserId(String id) {
        return userService.getUserEntity(id).getCustomer();
    }

    @Override
    public Customer getCustomerEntityThroughCustomerId(long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        }
        throw new RecordNotFoundException("cant find user with id: "+id);
    }


    @Override
    public ResponseEntity<?> deleteCustomer(long id) {

        Optional<Customer> customerFromDb = customerRepository.findById(id);

        if (customerFromDb.isPresent()) {
            customerRepository.deleteById(id);
            return ResponseEntity.status(200).body("User with id " + id + " successfully deleted");
        }

        return ResponseEntity.status(500).body("User with id " + id + " not found.");

    }

    @Override
    public void saveCustomerInDb(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public ResponseEntity<?> updateCustomer(String bearerToken, Customer customer) {

        String jwtString = jwtUtil.internalParseJwt(bearerToken);
        String id = jwtUtil.getUsernameFromJwtToken(jwtString);

        Customer customerEntityThroughUserId = getCustomerEntityThroughUserId(id);

        customerEntityThroughUserId.setFirstName(customer.getFirstName());
        customerEntityThroughUserId.setLastName(customer.getLastName());

        saveCustomerInDb(customerEntityThroughUserId);

        return ResponseEntity.status(200).body("Customer data of user " + id + " updated");

    }

    @Override
    public ResponseEntity<?> getCustomerDataResponse(String bearerToken) {
        String jwtString;
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_TYPE)) {
            jwtString = bearerToken.substring(TOKEN_TYPE.length());
        } else {
            return null;
        }
        return ResponseEntity.status(200).body(
                getCustomerEntityThroughUserId(jwtUtil.getUsernameFromJwtToken(jwtString))
        );
    }

    @Override
    public ResponseEntity<?> getCustomerReservationsByUserId(String id) {
        return ResponseEntity
                .status(200)
                .body(getCustomerEntityThroughUserId(id).getCustomerReservationList());
    }
}
