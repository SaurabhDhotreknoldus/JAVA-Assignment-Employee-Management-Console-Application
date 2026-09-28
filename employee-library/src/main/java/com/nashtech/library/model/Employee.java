package com.nashtech.library.model;

import com.nashtech.library.exception.InvalidEmployeeDataException;

import java.io.Serializable;
import java.util.Objects;

/**
 * Core reusable Employee domain model.
 * Implements Serializable so instances can be stored and retrieved from distributed caches (e.g. Redis).
 */
public class Employee implements Comparable<Employee>, Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String department;
    private double salary;
    private boolean active;

    public Employee() {
    }

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

    public EmployeeRecord toRecord() {
        return new EmployeeRecord(this.id, this.name, this.department, this.salary, this.active);
    }

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

    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return String.format("%d | %s | %s | %.2f | %s",
                id, name, department, salary, active ? "Active" : "Inactive");
    }
}

