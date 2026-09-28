package com.nashtech.library.dto;

import java.io.Serializable;

/**
 * Data Transfer Object for creating or updating an Employee via REST APIs.
 */
public class EmployeeRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String department;
    private Double salary;
    private Boolean active;

    public EmployeeRequestDto() {
    }

    public EmployeeRequestDto(Integer id, String name, String department, Double salary, Boolean active) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "EmployeeRequestDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                ", active=" + active +
                '}';
    }
}

