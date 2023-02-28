package se.kth.iv1201.appserv.jobapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.kth.iv1201.appserv.jobapp.domain.User;
import se.kth.iv1201.appserv.jobapp.domain.external.request.RegisterRequest;
import se.kth.iv1201.appserv.jobapp.domain.external.request.LogInRequest;
import se.kth.iv1201.appserv.jobapp.domain.external.response.AuthenticationResponse;
import se.kth.iv1201.appserv.jobapp.domain.external.response.GenericResponse;
import se.kth.iv1201.appserv.jobapp.exceptions.IllegalUserAuthenticationException;
import se.kth.iv1201.appserv.jobapp.exceptions.IllegalUserRegisterException;
import se.kth.iv1201.appserv.jobapp.service.UserService;

import java.util.List;

/**
 * Class responsible to communicate with the Front End with
 * requests related to user and user authorization management.
 */
@RestController
@CrossOrigin(origins = "https://frontendjobbapp.herokuapp.com/")
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Method to retrieve all users from the database to the Front End.
     *
     * @return JSON-object containing all users.
     */
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    /**
     * Method used to register a new user from the Front End and post the information in the database.
     *
     * @param request request-body containing information to be updated in the database.
     * @return an HTTP-status code to inform the Front End how the transaction went together with an
     * authentication token.
     */
    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) throws IllegalUserRegisterException {

        return userService.register(request);
    }

    /**
     * Method used to authenticate and login a user to the webpage from the Front End by checking
     * the credentials in the database.
     *
     * @param request the request-body containing user credentials.
     * @return an HTTP-status code to inform the Front End how the transaction went, together with an
     * authentication token.
     */
    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody LogInRequest request) throws IllegalUserAuthenticationException {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @GetMapping("get")
    public GenericResponse sayHello(){
        return GenericResponse.OK;
    }

}
