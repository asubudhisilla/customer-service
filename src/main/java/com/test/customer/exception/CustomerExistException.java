package com.test.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND)
public class CustomerExistException extends RuntimeException {

    public CustomerExistException() {
        super();
    }

    public CustomerExistException(String message) {
        super(message);
    }

    public CustomerExistException(Throwable cause) {
        super(cause);
    }

    public CustomerExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
