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

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {IllegalUserAuthenticationException.class} )
    public ResponseEntity <ErrorMessage> handleUserAuthenticationException(IllegalUserAuthenticationException e, WebRequest request){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = {IllegalJobApplicationUpdateException.class, OptimisticLockException.class} )
    public ResponseEntity <ErrorMessage> handleApplicationException(IllegalJobApplicationUpdateException e, WebRequest request){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.PRECONDITION_FAILED.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(value = {IllegalUserRegisterException.class} )
    public ResponseEntity <ErrorMessage> handleRegisterException(IllegalUserRegisterException e, WebRequest request){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {CannotCreateTransactionException.class})
    public ResponseEntity<?> cannotCreateTransactionException(CannotCreateTransactionException exception) {
        if (exception.contains(ConnectException.class)) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ExceptionHandler(value = {SQLException.class})
    public ResponseEntity<?> generalSQLException(CannotCreateTransactionException e, WebRequest request)  {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                "An unknown error occurred updating the database.",
                request.getDescription(false));
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

