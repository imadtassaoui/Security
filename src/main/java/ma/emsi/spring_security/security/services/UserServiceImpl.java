package ma.emsi.spring_security.security.services;

import lombok.AllArgsConstructor;
import ma.emsi.spring_security.security.entities.Role;
import ma.emsi.spring_security.security.entities.User;
import ma.emsi.spring_security.security.repositories.RoleRepository;
import ma.emsi.spring_security.security.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public User saveUser(String username, String password, String email, String confirmedPassword) {
        User user = userRepository.findByUsername(username);
        if(user != null)
            throw new RuntimeException("User already exist !");
        if(!password.equals(confirmedPassword))
            throw new RuntimeException("Password not match");

        user = User.builder()
                .Id(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(String role) {
        Role role1 = roleRepository.findById(role).orElse(null);
        if(role1 != null)
            throw new RuntimeException("Role already exist !");

        role1 = Role.builder()
                .role(role)
                .build();
        return roleRepository.save(role1);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        User user = userRepository.findByUsername(username);
        Role role1 = roleRepository.findById(role).get();
        user.getRoles().add(role1);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        User user = userRepository.findByUsername(username);
        Role role1 = roleRepository.findById(role).get();
        user.getRoles().remove(role1);
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
