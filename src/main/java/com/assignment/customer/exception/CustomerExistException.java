package com.assignment.customer.exception;

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
