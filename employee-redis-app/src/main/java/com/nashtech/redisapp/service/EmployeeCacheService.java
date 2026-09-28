package com.nashtech.redisapp.service;

import com.nashtech.library.dto.EmployeeRequestDto;
import com.nashtech.library.dto.EmployeeResponseDto;

import java.util.List;

/**
 * Service interface defining business operations for Employees with Redis caching.
 */
public interface EmployeeCacheService {

    /**
     * Requirement 9: Read employee by ID with @Cacheable.
     * Caches response in Redis under "employees" cache using employee ID as key.
     * Subsequent requests for the same ID are served directly from Redis.
     */
    EmployeeResponseDto getEmployeeById(int id);

    /**
     * Creates and persists a new employee to the database.
     */
    EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto);

    /**
     * Requirement 10: Update employee with @CachePut.
     * Updates database record and simultaneously refreshes the cached entry in Redis.
     */
    EmployeeResponseDto updateEmployee(int id, EmployeeRequestDto requestDto);

    /**
     * Requirement 11: Delete employee with @CacheEvict.
     * Removes employee from database and immediately invalidates/removes the key from Redis.
     */
    void deleteEmployee(int id);

    /**
     * Retrieves all employees from the database.
     */
    List<EmployeeResponseDto> getAllEmployees();

    /**
     * Filters employees by department.
     */
    List<EmployeeResponseDto> getEmployeesByDepartment(String department);

    /**
     * Filters active employees earning greater than minimum salary threshold.
     */
    List<EmployeeResponseDto> getActiveEmployeesWithSalaryGreaterThan(double minSalary);

    /**
     * Debug helper to inspect active Redis cache keys.
     */
    List<String> getActiveCacheKeys();
}

