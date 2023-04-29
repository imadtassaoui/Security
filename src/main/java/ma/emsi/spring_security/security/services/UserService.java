package ma.emsi.spring_security.security.services;

import ma.emsi.spring_security.security.entities.Role;
import ma.emsi.spring_security.security.entities.User;

public interface UserService {
    User saveUser(String username, String password, String email, String confirmedPassword);
    Role saveRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);
    User loadUserByUsername(String username);
}
