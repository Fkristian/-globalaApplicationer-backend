package se.kth.iv1201.appserv.jobapp.domain.external.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing the User-DTO from the request-body of attempting to log in
 * on the Front End.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogInRequest {
    String username;
    String password;
}
