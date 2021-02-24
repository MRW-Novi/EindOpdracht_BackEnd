package nl.randomstuff.eindopdracht.Service;


import nl.randomstuff.eindopdracht.model.Customer;
import nl.randomstuff.eindopdracht.model.User;
import nl.randomstuff.eindopdracht.model.Venue;
import nl.randomstuff.eindopdracht.repository.UserRepository;
import nl.randomstuff.eindopdracht.service.UserService;
import nl.randomstuff.eindopdracht.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest {

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserRepository userRepository;

    private User user;
    private User newUser;
    private Customer customer;
    private Venue newVenue;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("sjaak");

        customer = new Customer();

        newUser = new User();
        newUser.setUsername("sjaak");
//        newUser.addAuthority(new Authority(newUser.getUsername(), "Customer"));
        newUser.setCustomer(customer);

        newVenue = new Venue();

        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testGetUserEntityByUsername() {
//        Mockito.when(userRepository.findById("sjaak")).thenReturn(Optional.of(user));
//        assertThat(userService.getUserEntity("sjaak").getUsername())
//                .isEqualTo(user.getUsername());
//    }
//
//    @Test
//    public void testCreateUserAsCustomer() {
//        Mockito.when(userRepository.save(user))
//                .thenReturn(newUser);
//        Mockito.when(userRepository.findById("sjaak"))
//                .thenReturn(Optional.of(newUser));
//
//        assertThat(userService.createUserAsCustomer(user).getBody()).isEqualTo(newUser);
//    }
}
