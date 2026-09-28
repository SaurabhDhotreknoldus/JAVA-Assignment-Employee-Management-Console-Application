package com.nashtech.library.exception;

/**
 * Custom exception thrown when employee validation fails.
 */
public class InvalidEmployeeDataException extends RuntimeException {

    public InvalidEmployeeDataException(String message) {
        super(message);
    }

    public InvalidEmployeeDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

