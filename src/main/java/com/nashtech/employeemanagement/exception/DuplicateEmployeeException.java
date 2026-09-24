package com.nashtech.employeemanagement.exception;

/**
 * Custom unchecked exception thrown when an attempt is made to register
 * an employee with an ID that already exists.
 */
public class DuplicateEmployeeException extends RuntimeException {

    private final int employeeId;

    public DuplicateEmployeeException(int employeeId) {
        super("An employee with ID " + employeeId + " already exists in the system.");
        this.employeeId = employeeId;
    }

    public DuplicateEmployeeException(String message) {
        super(message);
        this.employeeId = -1;
    }

    public int getEmployeeId() {
        return employeeId;
    }
}
