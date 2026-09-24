package com.nashtech.employeemanagement.exception;

/**
 * Custom unchecked exception thrown when employee validation fails
 * due to invalid arguments (e.g. empty name, negative salary, invalid ID).
 */
public class InvalidEmployeeDataException extends RuntimeException {

    public InvalidEmployeeDataException(String message) {
        super(message);
    }

    public InvalidEmployeeDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
