package com.nashtech.library.exception;

/**
 * Custom exception thrown when an employee cannot be found by ID or criteria.
 */
public class EmployeeNotFoundException extends RuntimeException {

    private final Integer employeeId;

    public EmployeeNotFoundException(String message) {
        super(message);
        this.employeeId = null;
    }

    public EmployeeNotFoundException(int employeeId) {
        super("Employee not found with ID: " + employeeId);
        this.employeeId = employeeId;
    }

    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.employeeId = null;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }
}

