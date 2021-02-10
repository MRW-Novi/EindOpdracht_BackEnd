package nl.randomstuff.eindopdracht.service;

import nl.randomstuff.eindopdracht.model.Authority;
import nl.randomstuff.eindopdracht.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;//TODO: kan Boromir niet vinden.

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userService.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        String password = user.get().getPassword();

        Set<Authority> authorities = user.get().getAuthorities();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority :
                authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }

        return new User(username, password, grantedAuthorities); //TODO: wellicht dat er een constr. mist? voorbeeld heeft geen constr. in user.class
    }
}
