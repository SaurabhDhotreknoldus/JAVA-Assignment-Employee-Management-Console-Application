package com.nashtech.employeemanagement.repository;

import com.nashtech.employeemanagement.model.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) / Repository interface for Employee persistence operations.
 * Demonstrates Abstraction and Interface-based architecture.
 */
public interface EmployeeRepository {

    /**
     * Saves an employee in the repository.
     *
     * @param employee the employee to save
     * @return the saved employee
     */
    Employee save(Employee employee);

    /**
     * Finds an employee by their unique ID.
     * Demonstrates modern Java Optional return type to handle presence/absence cleanly.
     *
     * @param id employee identifier
     * @return Optional containing employee if found, or empty Optional
     */
    Optional<Employee> findById(int id);

    /**
     * Retrieves all employees stored in the repository.
     *
     * @return List of all employees
     */
    List<Employee> findAll();

    /**
     * Checks if an employee with the given ID exists.
     *
     * @param id employee identifier
     * @return true if exists, false otherwise
     */
    boolean existsById(int id);

    /**
     * Deletes an employee by their ID.
     *
     * @param id employee identifier
     * @return true if an employee was removed, false otherwise
     */
    boolean deleteById(int id);

    /**
     * Returns the total count of employees.
     *
     * @return number of stored employees
     */
    int count();

    /**
     * Clears all employees from repository.
     */
    void clear();
}
