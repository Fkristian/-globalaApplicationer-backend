package se.kth.iv1201.appserv.jobapp.domain.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing the Application-DTO from the request-body, containing the information
 * about competences.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Competences {
    String competence;
    String yearsOfExperience;
}
