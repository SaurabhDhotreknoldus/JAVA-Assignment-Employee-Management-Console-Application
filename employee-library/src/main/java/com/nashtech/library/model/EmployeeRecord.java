package com.nashtech.library.model;

import java.io.Serializable;

/**
 * Reusable Modern Java Record for Employee data representation.
 */
public record EmployeeRecord(
        int id,
        String name,
        String department,
        double salary,
        boolean active
) implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public Employee toEntity() {
        return new Employee(id, name, department, salary, active);
    }
}

