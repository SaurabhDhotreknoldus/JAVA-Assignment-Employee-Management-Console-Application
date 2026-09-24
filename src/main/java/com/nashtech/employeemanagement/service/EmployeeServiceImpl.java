package com.nashtech.employeemanagement.service;

import com.nashtech.employeemanagement.exception.DuplicateEmployeeException;
import com.nashtech.employeemanagement.exception.EmployeeNotFoundException;
import com.nashtech.employeemanagement.exception.InvalidEmployeeDataException;
import com.nashtech.employeemanagement.model.Employee;
import com.nashtech.employeemanagement.repository.EmployeeRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of EmployeeService containing core business logic.
 * Demonstrates:
 * - OOP Abstraction & Encapsulation
 * - Java Collections Framework
 * - Stream API & Lambda Expressions
 * - Custom Exception Handling
 * - Modern Optional handling
 */
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * Constructor injection for EmployeeRepository (Dependency Injection).
     *
     * @param employeeRepository repository implementation
     */
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = Objects.requireNonNull(employeeRepository, "EmployeeRepository must not be null");
    }

    @Override
    public Employee addEmployee(Employee employee) {
        if (employee == null) {
            throw new InvalidEmployeeDataException("Employee object cannot be null.");
        }
        if (employeeRepository.existsById(employee.getId())) {
            throw new DuplicateEmployeeException(employee.getId());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee addEmployee(int id, String name, String department, double salary, boolean active) {
        Employee employee = new Employee(id, name, department, salary, active);
        return addEmployee(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public Optional<Employee> findEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new InvalidEmployeeDataException("Department name cannot be null or empty.");
        }
        final String searchDept = department.trim();

        // Stream API & Lambda filtering
        return employeeRepository.findAll().stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(searchDept))
                .toList();
    }

    @Override
    public List<Employee> getActiveEmployeesWithSalaryGreaterThan(double minSalary) {
        if (Double.isNaN(minSalary) || minSalary < 0) {
            throw new InvalidEmployeeDataException("Minimum salary threshold cannot be negative: " + minSalary);
        }

        // Stream API demonstration explicitly requested in assignment:
        // employees.stream()
        //          .filter(Employee::isActive)
        //          .filter(e -> e.getSalary() > minSalary)
        //          .toList()
        return employeeRepository.findAll().stream()
                .filter(Employee::isActive)
                .filter(e -> e.getSalary() > minSalary)
                .toList();
    }

    @Override
    public void loadSampleEmployees() {
        // Sample dataset specified in assignment requirement:
        // 101 | Alex  | Engineering | 90000  | Active
        // 102 | Sam   | Engineering | 125000 | Active
        // 103 | John  | Finance     | 140000 | Inactive
        // 104 | Priya | Engineering | 150000 | Active
        List<Employee> sampleData = List.of(
                new Employee(101, "Alex", "Engineering", 90000.0, true),
                new Employee(102, "Sam", "Engineering", 125000.0, true),
                new Employee(103, "John", "Finance", 140000.0, false),
                new Employee(104, "Priya", "Engineering", 150000.0, true)
        );

        for (Employee emp : sampleData) {
            if (!employeeRepository.existsById(emp.getId())) {
                employeeRepository.save(emp);
            }
        }
    }

    @Override
    public boolean deleteEmployee(int id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        return employeeRepository.deleteById(id);
    }

    @Override
    public int getTotalEmployeeCount() {
        return employeeRepository.count();
    }
}
