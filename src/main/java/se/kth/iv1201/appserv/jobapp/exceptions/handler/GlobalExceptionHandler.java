package se.kth.iv1201.appserv.jobapp.exceptions.handler;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
package se.kth.iv1201.appserv.jobapp.exceptions.handler;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import jakarta.persistence.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Method that handles user and user authentication related exceptions by returning a
     * status code to the Front End.
     * @param e the exception thrown related to user authentication errors.
     * @param request the web-request done while attempting operations.
     * @return a response-entity containing the error-message and status code.
     */
    @ExceptionHandler(value = {IllegalUserAuthenticationException.class} )
    public ResponseEntity <?> handleUserAuthenticationException(IllegalUserAuthenticationException e, WebRequest request){
        LOGGER.warn(HttpStatus.UNAUTHORIZED+ " error caused by: " +e.getMessage()+
                "\tAccessing: " +request.getDescription(false));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    /**
     * Method that handles application submission related exceptions by returning a
     * status code to the Front End.
     * @param e the exception thrown related to application errors.
     * @param request the web-request done while attempting operations.
     * @return a response-entity containing the error-message and status code.
     */
    @ExceptionHandler(value = {IllegalJobApplicationUpdateException.class, OptimisticLockException.class} )
    public ResponseEntity <?> handleApplicationException(IllegalJobApplicationUpdateException e, WebRequest request){
        String msg;
        if(e != null) {
            msg = e.getMessage();
        }
        else{
            msg = "Someone already tried to update that user.";
        }
        LOGGER.warn(HttpStatus.PRECONDITION_FAILED+
                " error caused by: " +msg+
                "\tAccessing: " +request.getDescription(false));
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
    }

    /**
     * Method that handles user registration related exceptions by returning a
     * status code to the Front End.
     * @param e the exception thrown related to user registration errors.
     * @param request the web-request done while attempting operations.
     * @return a response-entity containing the error-message and status code.
     */
    @ExceptionHandler(value = {IllegalUserRegisterException.class} )
    public ResponseEntity <?> handleRegisterException(IllegalUserRegisterException e, WebRequest request){
        LOGGER.warn(HttpStatus.CONFLICT+ " error caused by: " +e.getMessage()+
                "\tAccessing: " +request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    /**
     * Method that handles exceptions if the database would potentially crash.
     * @param e the exception thrown if the database would go down.
     * @return status code depending on how it went down.
     */
    @ExceptionHandler(value = {CannotCreateTransactionException.class})
    public ResponseEntity <?> cannotCreateTransactionException(CannotCreateTransactionException e) {
        if (e.contains(ConnectException.class)) {
            LOGGER.error(HttpStatus.SERVICE_UNAVAILABLE +" error caused by no connection to the database.");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }else {
            LOGGER.error(HttpStatus.INTERNAL_SERVER_ERROR+" caused by database failure.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Method that handles general SQL exceptions thrown when a transaction might not complete.
     * @param request the web-request done while attempting operations.
     * @return a response-entity containing the error-message and status code.
     */
    @ExceptionHandler(value = {SQLException.class, NullPointerException.class})
    public ResponseEntity <?> generalSQLException(WebRequest request)  {
        LOGGER.error(HttpStatus.INTERNAL_SERVER_ERROR.value()+ " " +HttpStatus.INTERNAL_SERVER_ERROR+ " error caused by: An unknown error occurred updating the database."+
                "\tAccessing: " +request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Method that handles exceptions related to the JWT token and authentication.
     * @param request the web-request done while attempting operations.
     * @return a response-entity containing the error-message and status code.
     */
    @ExceptionHandler(value = {MalformedJwtException.class, PrematureJwtException.class})
    public ResponseEntity <?> generalTokenException(WebRequest request)  {
        LOGGER.warn(HttpStatus.UNAUTHORIZED.value()+ " " +HttpStatus.UNAUTHORIZED+ " error caused by: An error occurred when validating the token. " +
                "\tAccessing: " +request.getDescription(false));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

