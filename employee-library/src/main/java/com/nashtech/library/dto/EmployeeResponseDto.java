package com.nashtech.library.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Data Transfer Object for returning Employee details from REST APIs.
 * Implements Serializable to allow seamless Redis caching and deserialization.
 */
public class EmployeeResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String department;
    private double salary;
    private boolean active;

    public EmployeeResponseDto() {
    }

    public EmployeeResponseDto(int id, String name, String department, double salary, boolean active) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeResponseDto that = (EmployeeResponseDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "EmployeeResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                ", active=" + active +
                '}';
    }
}

