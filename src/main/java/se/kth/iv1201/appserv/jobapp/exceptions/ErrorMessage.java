package se.kth.iv1201.appserv.jobapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * A class representing an error-message that can be sent to the Front End.
 */
@Data
@AllArgsConstructor
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
