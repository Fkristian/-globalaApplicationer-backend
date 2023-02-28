package se.kth.iv1201.appserv.jobapp.domain.external.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing the User-DTO from the request-body by registering a new account
 * on the Front End.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String firstname;
    String lastname;
    String emailaddress;
    String personnumber;
    String username;
    String password;
}
