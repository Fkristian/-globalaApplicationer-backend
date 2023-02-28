package se.kth.iv1201.appserv.jobapp.domain.external.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.kth.iv1201.appserv.jobapp.domain.Competence;
import se.kth.iv1201.appserv.jobapp.domain.CompetenceProfile;

import java.util.List;

/**
 * Class representing the Application-DTO as a response to the Front End.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseDTO {
    int personId;
    String firstName;
    String lastName;
    String personNumber;
    String email;
    List <Competence> competences;
    List <CompetenceProfile> competenceProfiles;
}
