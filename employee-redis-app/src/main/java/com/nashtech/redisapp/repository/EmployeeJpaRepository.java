package com.nashtech.redisapp.repository;

import com.nashtech.redisapp.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for database persistence operations on EmployeeEntity.
 */
@Repository
public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, Integer> {

    /**
     * Finds employees by department name (case-insensitive).
     */
    List<EmployeeEntity> findByDepartmentIgnoreCase(String department);

    /**
     * Finds active employees with salary strictly greater than minSalary.
     */
    List<EmployeeEntity> findByActiveTrueAndSalaryGreaterThan(Double minSalary);
}

