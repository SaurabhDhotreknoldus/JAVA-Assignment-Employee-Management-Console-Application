package com.nashtech.redisapp.entity;

import com.nashtech.library.dto.EmployeeResponseDto;
import com.nashtech.library.model.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

/**
 * JPA Entity representing the Employee record stored in the database.
 */
@Entity
@Table(name = "employees")
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "department", nullable = false, length = 100)
    private String department;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @Column(name = "active", nullable = false)
    private Boolean active;

    public EmployeeEntity() {
    }

    public EmployeeEntity(Integer id, String name, String department, Double salary, Boolean active) {
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

    /**
     * Converts JPA entity to Employee domain model from employee-library.
     */
    public Employee toDomain() {
        return new Employee(this.id, this.name, this.department, this.salary, this.active);
    }

    /**
     * Converts JPA entity to EmployeeResponseDto.
     */
    public EmployeeResponseDto toResponseDto() {
        return new EmployeeResponseDto(this.id, this.name, this.department, this.salary, this.active);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

