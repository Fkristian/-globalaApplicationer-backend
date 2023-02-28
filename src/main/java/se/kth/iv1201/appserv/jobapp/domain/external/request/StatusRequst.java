package se.kth.iv1201.appserv.jobapp.domain.external.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing the ApplicationStatus-DTO from the request-body of updating an application
 * from the Front End.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusRequst {
    int personId;
    String status;
    int version;
}
