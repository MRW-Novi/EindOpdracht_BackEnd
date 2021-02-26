package nl.randomstuff.eindopdracht.controller;

import nl.randomstuff.eindopdracht.service.CustomerService;
import nl.randomstuff.eindopdracht.service.UserService;
import nl.randomstuff.eindopdracht.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/admin")
@RestController
public class AdminController {

    UserService userService;
    VenueService venueService;
    CustomerService customerService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setVenueService(VenueService venueService) {
        this.venueService = venueService;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }


}
