package com.nashtech.employeemanagement.model;

/**
 * Modern Java Record demonstrating Java 14+ Record feature.
 * Provides an immutable data carrier for Employee information.
 */
public record EmployeeRecord(
        int id,
        String name,
        String department,
        double salary,
        boolean active
) {
    /**
     * Compact constructor validating record invariants.
     */
    public EmployeeRecord {
        if (id <= 0) {
            throw new IllegalArgumentException("Record Employee ID must be positive: " + id);
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Record Employee name cannot be blank");
        }
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Record Employee department cannot be blank");
        }
        if (salary < 0) {
            throw new IllegalArgumentException("Record Employee salary cannot be negative: " + salary);
        }
    }

    /**
     * Converts this immutable record back to an Employee entity.
     *
     * @return mutable Employee entity instance
     */
    public Employee toEntity() {
        return new Employee(id, name, department, salary, active);
    }
}
