package com.nashtech.employeemanagement.exception;

/**
 * Custom unchecked exception thrown when an employee with a specified identifier
 * or criteria cannot be found in the system.
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
