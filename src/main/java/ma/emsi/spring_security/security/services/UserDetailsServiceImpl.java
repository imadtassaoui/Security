package ma.emsi.spring_security.security.services;

import lombok.AllArgsConstructor;
import ma.emsi.spring_security.security.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadUserByUsername(username);
        if(user == null) throw new UsernameNotFoundException(String.format("User %s not found", username));

        String[] roles = user.getRoles().stream()
                .map(r -> r.getRole())
                .toArray(String[]::new);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .build();

        return userDetails;
    }
}
