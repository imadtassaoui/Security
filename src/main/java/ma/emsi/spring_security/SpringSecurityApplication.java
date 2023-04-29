package ma.emsi.spring_security;

import ma.emsi.spring_security.security.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {

          UserDetails user = jdbcUserDetailsManager.loadUserByUsername("user1");
          if(user == null)
          jdbcUserDetailsManager.createUser(
                  User.withUsername("user1").password(passwordEncoder.encode("123")).roles("USER").build()
          );

          UserDetails admin = jdbcUserDetailsManager.loadUserByUsername("admin1");
          if(admin == null)
          jdbcUserDetailsManager.createUser(
                  User.withUsername("admin1").password(passwordEncoder.encode("123")).roles("USER","ADMIN").build()
          );
        };
    }

    //@Bean
    CommandLineRunner commandLineRunnerUsersDetails(UserService userService)
    {
        return args -> {
            userService.saveUser("user2","123","user@gmail.com","123");
            userService.saveUser("admin2","123","admin@gmail.com","123");

            userService.saveRole("USER");
            userService.saveRole("ADMIN");

            userService.addRoleToUser("user2","USER");
            userService.addRoleToUser("admin2","USER");
            userService.addRoleToUser("admin2","ADMIN");
        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
