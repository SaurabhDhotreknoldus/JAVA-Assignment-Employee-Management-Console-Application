package com.nashtech.employeemanagement.model;

import com.nashtech.employeemanagement.exception.InvalidEmployeeDataException;

import java.io.Serializable;
import java.util.Objects;

/**
 * Domain model representing an Employee.
 * Demonstrates OOP principles: Encapsulation, Data Validation,
 * custom equals/hashCode, and clean toString representation.
 */
public class Employee implements Comparable<Employee>, Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String department;
    private double salary;
    private boolean active;

    /**
     * Default constructor (useful for frameworks and serialization).
     */
    public Employee() {
    }

    /**
     * Parameterized constructor with validation.
     *
     * @param id         Unique employee identifier (must be positive)
     * @param name       Employee full name (cannot be null or blank)
     * @param department Department name (cannot be null or blank)
     * @param salary     Salary (must be non-negative)
     * @param active     Employment status (true = active, false = inactive)
     * @throws InvalidEmployeeDataException if any validation check fails
     */
    public Employee(int id, String name, String department, double salary, boolean active) {
        validateId(id);
        validateName(name);
        validateDepartment(department);
        validateSalary(salary);

        this.id = id;
        this.name = name.trim();
        this.department = department.trim();
        this.salary = salary;
        this.active = active;
    }

    // --- Validation Helpers ---

    private static void validateId(int id) {
        if (id <= 0) {
            throw new InvalidEmployeeDataException("Employee ID must be a positive integer. Provided: " + id);
        }
    }

    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidEmployeeDataException("Employee name cannot be null or empty.");
        }
    }

    private static void validateDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new InvalidEmployeeDataException("Employee department cannot be null or empty.");
        }
    }

    private static void validateSalary(double salary) {
        if (Double.isNaN(salary) || salary < 0) {
            throw new InvalidEmployeeDataException("Employee salary must be non-negative. Provided: " + salary);
        }
    }

    // --- Getters & Setters (Encapsulation) ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        validateId(id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        validateDepartment(department);
        this.department = department.trim();
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        validateSalary(salary);
        this.salary = salary;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Converts this Employee object to a modern Java Record representation.
     *
     * @return EmployeeRecord containing current employee details
     */
    public EmployeeRecord toRecord() {
        return new EmployeeRecord(this.id, this.name, this.department, this.salary, this.active);
    }

    // --- Object contract: equals & hashCode based on unique ID ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // --- Natural Ordering by ID ---

    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }

    // --- String Representation ---

    @Override
    public String toString() {
        return String.format("%d | %s | %s | %.2f | %s",
                id, name, department, salary, active ? "Active" : "Inactive");
    }
}
