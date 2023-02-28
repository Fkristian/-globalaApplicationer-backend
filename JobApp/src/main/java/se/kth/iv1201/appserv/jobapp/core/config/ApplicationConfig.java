package se.kth.iv1201.appserv.jobapp.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.kth.iv1201.appserv.jobapp.repository.UserRepository;

/**
 * Class containing configurations for user authentication.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;

    /**
     * Bean to extract the user details by locating it from the database.
     *
     * @return the user details.
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByUsername(username);
                //.orElseThrow(() -> UsernameNotFoundException("User not found")); //behöver optional i userRepository men det fuckar för userService
    }

    /**
     * Bean to provide authentication to the user details being added to the databse.
     *
     * @return the authentication provider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEndcoder());
        return authProvider;

    }

    /**
     * Bean to encode passwords.
     *
     * @return a new password encoder.
     */
    @Bean
    public PasswordEncoder passwordEndcoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean to retrieve a new authentication manager.
     *
     * @param config the configuration of the authentication manager.
     * @return the specified authentication manager.
     * @throws Exception thrown when a general error or failure occurs.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
