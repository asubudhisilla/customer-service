package com.test.customer.exception;

import com.test.customer.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    //specific exception
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(CustomerNotFoundException exception,
                                                                        WebRequest webRequest) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .details(webRequest.getDescription(false))
                .errorMessage(exception.getMessage())
                .timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerExistException.class)
    public ResponseEntity<ErrorDetails> handleResourceFoundException(CustomerExistException exception,
                                                                        WebRequest webRequest) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .details(webRequest.getDescription(false))
                .errorMessage(exception.getMessage())
                .timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(errorDetails, HttpStatus.FOUND);
    }

    // global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                              WebRequest webRequest) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .details(webRequest.getDescription(false))
                .errorMessage(exception.getMessage())
                .timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
