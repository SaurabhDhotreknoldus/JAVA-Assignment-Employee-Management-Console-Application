package com.nashtech.employeemanagement.service;

import com.nashtech.employeemanagement.exception.DuplicateEmployeeException;
import com.nashtech.employeemanagement.exception.EmployeeNotFoundException;
import com.nashtech.employeemanagement.exception.InvalidEmployeeDataException;
import com.nashtech.employeemanagement.model.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Service interface defining business operations for Employee Management.
 * Follows the Dependency Inversion Principle (DIP) and Separation of Concerns.
 */
public interface EmployeeService {

    /**
     * Requirement 1: Add an employee to the application.
     *
     * @param employee the employee domain object to add
     * @return the newly added employee
     * @throws DuplicateEmployeeException   if employee with ID already exists
     * @throws InvalidEmployeeDataException if data validation fails
     */
    Employee addEmployee(Employee employee);

    /**
     * Overloaded convenience method to create and add an employee directly.
     *
     * @param id         unique employee id
     * @param name       full name
     * @param department department name
     * @param salary     salary amount
     * @param active     status
     * @return the created and added employee
     */
    Employee addEmployee(int id, String name, String department, double salary, boolean active);

    /**
     * Requirement 2: Retrieve and display all employees.
     *
     * @return list of all employees
     */
    List<Employee> getAllEmployees();

    /**
     * Requirement 3 & 6: Search for an employee by ID.
     * Throws EmployeeNotFoundException when not found.
     *
     * @param id employee identifier
     * @return matching Employee
     * @throws EmployeeNotFoundException if employee does not exist
     */
    Employee getEmployeeById(int id);

    /**
     * Alternative search using Java Optional.
     *
     * @param id employee identifier
     * @return Optional of Employee
     */
    Optional<Employee> findEmployeeById(int id);

    /**
     * Requirement 4: Filter and display employees belonging to a specific department.
     * Uses Stream API for case-insensitive filtering.
     *
     * @param department department name to filter by
     * @return list of employees in specified department
     */
    List<Employee> getEmployeesByDepartment(String department);

    /**
     * Requirement 5: Display active employees whose salary is greater than a given value.
     * Uses Stream API and Lambda expressions as demonstrated in assignment specification:
     * employees.stream().filter(Employee::isActive).filter(e -> e.getSalary() > minSalary)
     *
     * @param minSalary threshold salary
     * @return list of active employees earning strictly more than minSalary
     */
    List<Employee> getActiveEmployeesWithSalaryGreaterThan(double minSalary);

    /**
     * Seeds the initial sample dataset as described in the assignment:
     * 101 | Alex  | Engineering |  90000 | Active
     * 102 | Sam   | Engineering | 125000 | Active
     * 103 | John  | Finance     | 140000 | Inactive
     * 104 | Priya | Engineering | 150000 | Active
     */
    void loadSampleEmployees();

    /**
     * Deletes an employee by ID.
     *
     * @param id employee identifier
     * @return true if deleted, false if not found
     */
    boolean deleteEmployee(int id);

    /**
     * Returns total count of registered employees.
     *
     * @return count of employees
     */
    int getTotalEmployeeCount();
}
