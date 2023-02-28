package se.kth.iv1201.appserv.jobapp.exceptions.handler;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import se.kth.iv1201.appserv.jobapp.exceptions.ErrorMessage;
import se.kth.iv1201.appserv.jobapp.exceptions.IllegalJobApplicationUpdateException;
import se.kth.iv1201.appserv.jobapp.exceptions.IllegalUserAuthenticationException;
import se.kth.iv1201.appserv.jobapp.exceptions.IllegalUserRegisterException;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.Date;

/**
 * Class that handles all specified exceptions thrown by the application by returning
 * given status codes to the Front End.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Method that handles user and user authentication related exceptions by returning a
     * status code to the Front End.
     * @param e the exception thrown related to user authentication errors.
     * @param request the web-request done while attempting operations.
     * @return a response-entity containing the error-message and status code.
     */
    @ExceptionHandler(value = {IllegalUserAuthenticationException.class} )
    public ResponseEntity <ErrorMessage> handleUserAuthenticationException(IllegalUserAuthenticationException e, WebRequest request){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }
    /**
     * Method that handles application submission related exceptions by returning a
     * status code to the Front End.
     * @param e the exception thrown related to application errors.
     * @param request the web-request done while attempting operations.
     * @return a response-entity containing the error-message and status code.
     */
    @ExceptionHandler(value = {IllegalJobApplicationUpdateException.class, OptimisticLockException.class} )
    public ResponseEntity <ErrorMessage> handleApplicationException(IllegalJobApplicationUpdateException e, WebRequest request){
        String msg;
        if(e != null) {
            msg = e.getMessage();
        }
        else{
            msg = "Someone already tried to update that user.";
        }
        ErrorMessage message = new ErrorMessage(
                HttpStatus.PRECONDITION_FAILED.value(),
                new Date(),
                msg,
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.PRECONDITION_FAILED);
    }

    /**
     * Method that handles user registration related exceptions by returning a
     * status code to the Front End.
     * @param e the exception thrown related to user registration errors.
     * @param request the web-request done while attempting operations.
     * @return a response-entity containing the error-message and status code.
     */
    @ExceptionHandler(value = {IllegalUserRegisterException.class} )
    public ResponseEntity <ErrorMessage> handleRegisterException(IllegalUserRegisterException e, WebRequest request){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    /**
     * Method that handles exceptions if the database would potentially crash.
     * @param e the exception thrown if the database would go down.
     * @return status code depending on how it went down.
     */
    @ExceptionHandler(value = {CannotCreateTransactionException.class})
    public ResponseEntity<?> cannotCreateTransactionException(CannotCreateTransactionException e) {
        if (e.contains(ConnectException.class)) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Method that handles general SQL exceptions thrown when a transaction might not complete.
     * @param request the web-request done while attempting operations.
     * @return a response-entity containing the error-message and status code.
     */
    @ExceptionHandler(value = {SQLException.class})
    public ResponseEntity<?> generalSQLException(WebRequest request)  {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                "An unknown error occurred updating the database.",
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method that handles exceptions related to the JWT token and authentication.
     * @param request the web-request done while attempting operations.
     * @return a response-entity containing the error-message and status code.
     */
    @ExceptionHandler(value = {MalformedJwtException.class, PrematureJwtException.class})
    public ResponseEntity<?> generalSQLException(WebRequest request)  {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                "An error occurred when validating the token.",
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }
}

