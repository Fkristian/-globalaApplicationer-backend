package se.kth.iv1201.appserv.jobapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.appserv.jobapp.domain.ApplicationStatus;
import se.kth.iv1201.appserv.jobapp.domain.User;
import se.kth.iv1201.appserv.jobapp.domain.external.request.RegisterRequest;
import se.kth.iv1201.appserv.jobapp.domain.external.request.LogInRequest;
import se.kth.iv1201.appserv.jobapp.domain.external.response.AuthenticationResponse;
import se.kth.iv1201.appserv.jobapp.exceptions.IllegalUserAuthenticationException;
import se.kth.iv1201.appserv.jobapp.exceptions.IllegalUserRegisterException;
import se.kth.iv1201.appserv.jobapp.repository.ApplicationStatusRepository;
import se.kth.iv1201.appserv.jobapp.repository.UserRepository;

import java.util.List;

/**
 * Service class where the business logic related to user and user authentication management occurs.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ApplicationStatusRepository applicationStatusRepository;

    /**
     * Method used to insert a new user into the database.
     *
     * @param request the request-DTO containing information to be inserted into the database.
     * @return an HTTP-status code to inform the Front End how the transaction went together with an
     * authentication token, if the transaction completed.
     */
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) throws IllegalUserRegisterException {
        if(userRepository.findByUsername(request.getUsername()) == null) {
            var user = User.builder()
                    .name(request.getFirstname())
                    .email(request.getEmailaddress())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .pnr(request.getPersonnumber())
                    .roleId(2)
                    .surname(request.getLastname())
                    .username(request.getUsername())
                    .build();
            int personId = userRepository.save(user).getPersonId();

            var status = ApplicationStatus.builder()
                    .personId(personId)
                    .status("notapplied")
                    .build();
            applicationStatusRepository.save(status);

            var jwtToken = jwtService.generateToken(user);
            return ResponseEntity.ok(AuthenticationResponse.builder().token(jwtToken).build());
        } else {
            throw new IllegalUserRegisterException("A user already exists with the username: " +request.getUsername());
        }
    }

    /**
     * Method used to authenticate a user by validating the request credentials with the information stored in the
     * database.
     * @param request the request-DTO containing information to be validated.
     * @return an HTTP-status code to inform the Front End how the transaction went together with an
     * authentication token, if the transaction completed.
     */
    public AuthenticationResponse authenticate(LogInRequest request) throws IllegalUserAuthenticationException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername());
        if(user == null) {
            throw new IllegalUserAuthenticationException("The user with the " + request.getUsername() + " could not be found.");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    /**
     * Method used to retrieve all the persons from the database.
     *
     * @return all the persons as a List of {@code User} type objects.
     */
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}

