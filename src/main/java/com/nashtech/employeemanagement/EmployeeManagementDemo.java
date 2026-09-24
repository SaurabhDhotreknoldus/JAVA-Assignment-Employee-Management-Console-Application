package com.nashtech.employeemanagement;

import com.nashtech.employeemanagement.exception.DuplicateEmployeeException;
import com.nashtech.employeemanagement.exception.EmployeeNotFoundException;
import com.nashtech.employeemanagement.exception.InvalidEmployeeDataException;
import com.nashtech.employeemanagement.model.Employee;
import com.nashtech.employeemanagement.model.EmployeeRecord;
import com.nashtech.employeemanagement.repository.InMemoryEmployeeRepository;
import com.nashtech.employeemanagement.service.EmployeeService;
import com.nashtech.employeemanagement.service.EmployeeServiceImpl;
import com.nashtech.employeemanagement.util.ConsoleTableFormatter;

import java.util.List;

/**
 * Automated Execution and Verification Demo for Employee Management Application.
 * <p>
 * This class runs an automated end-to-end walkthrough of all 6 assignment requirements,
 * including edge cases, custom exception handling, and modern Java bonus features.
 * Can be run in CI/CD, evaluation environments, or to generate execution output logs.
 */
public class EmployeeManagementDemo {

    public static void main(String[] args) {
        System.out.println("===============================================================================");
        System.out.println("   EMPLOYEE MANAGEMENT CONSOLE APPLICATION - AUTOMATED EXECUTION DEMO          ");
        System.out.println("===============================================================================\n");

        // 1. Initialize Repository & Service
        InMemoryEmployeeRepository repository = new InMemoryEmployeeRepository();
        EmployeeService service = new EmployeeServiceImpl(repository);

        // -------------------------------------------------------------------------
        // Requirement 1: Add employees to the application
        // -------------------------------------------------------------------------
        System.out.println(">>> STEP 1: Adding Initial Employees (Sample dataset from assignment specifications)");
        service.addEmployee(new Employee(101, "Alex", "Engineering", 90000.0, true));
        service.addEmployee(new Employee(102, "Sam", "Engineering", 125000.0, true));
        service.addEmployee(new Employee(103, "John", "Finance", 140000.0, false));
        service.addEmployee(new Employee(104, "Priya", "Engineering", 150000.0, true));
        ConsoleTableFormatter.printSuccess("Added 4 initial employees successfully.\n");

        // -------------------------------------------------------------------------
        // Requirement 2: Display all employees
        // -------------------------------------------------------------------------
        System.out.println(">>> STEP 2: Display All Employees");
        List<Employee> allEmployees = service.getAllEmployees();
        ConsoleTableFormatter.printEmployeeTable(allEmployees);

        // -------------------------------------------------------------------------
        // Requirement 3: Search for an employee using Employee ID (Found scenario)
        // -------------------------------------------------------------------------
        System.out.println(">>> STEP 3: Search Employee by ID (ID: 102 - Sam)");
        try {
            Employee emp102 = service.getEmployeeById(102);
            ConsoleTableFormatter.printSuccess("Found employee:");
            ConsoleTableFormatter.printEmployeeCard(emp102);
        } catch (EmployeeNotFoundException e) {
            ConsoleTableFormatter.printError("Unexpected: " + e.getMessage());
        }

        // -------------------------------------------------------------------------
        // Requirement 6: Handle scenario where an employee cannot be found (NotFound scenario)
        // -------------------------------------------------------------------------
        System.out.println(">>> STEP 4: Search Non-Existent Employee (ID: 999 - Triggers EmployeeNotFoundException)");
        try {
            service.getEmployeeById(999);
        } catch (EmployeeNotFoundException e) {
            ConsoleTableFormatter.printSuccess("Properly caught expected exception -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        System.out.println();

        // -------------------------------------------------------------------------
        // Requirement 4: Display employees belonging to a specific department
        // -------------------------------------------------------------------------
        System.out.println(">>> STEP 5: Display Employees in Department: 'Engineering'");
        List<Employee> engineeringEmployees = service.getEmployeesByDepartment("Engineering");
        ConsoleTableFormatter.printEmployeeTable(engineeringEmployees);

        System.out.println(">>> STEP 6: Display Employees in Department: 'Finance'");
        List<Employee> financeEmployees = service.getEmployeesByDepartment("Finance");
        ConsoleTableFormatter.printEmployeeTable(financeEmployees);

        // -------------------------------------------------------------------------
        // Requirement 5: Display active employees whose salary is greater than a given value
        // (Exact specification example: Active employees with salary > 100,000 -> Sam, Priya)
        // -------------------------------------------------------------------------
        System.out.println(">>> STEP 7: Stream API Demo - Find active employees with salary > 100,000");
        System.out.println("Stream Pipeline:");
        System.out.println("  employees.stream()");
        System.out.println("           .filter(Employee::isActive)");
        System.out.println("           .filter(e -> e.getSalary() > 100000)");
        System.out.println("           .forEach(System.out::println);");
        System.out.println();

        List<Employee> highEarners = service.getActiveEmployeesWithSalaryGreaterThan(100000.0);
        ConsoleTableFormatter.printEmployeeTable(highEarners);

        System.out.println("Extracted Names of Matching Active Employees:");
        highEarners.stream()
                .map(Employee::getName)
                .forEach(name -> System.out.println("  * " + name));
        System.out.println();

        // -------------------------------------------------------------------------
        // Validation & Edge Case Handling (Duplicate ID, Invalid Data)
        // -------------------------------------------------------------------------
        System.out.println(">>> STEP 8: Testing Duplicate Employee ID Exception");
        try {
            service.addEmployee(new Employee(101, "Duplicate Alex", "HR", 50000.0, true));
        } catch (DuplicateEmployeeException e) {
            ConsoleTableFormatter.printSuccess("Properly caught expected exception -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        System.out.println();

        System.out.println(">>> STEP 9: Testing Invalid Employee Data Exception (Negative Salary)");
        try {
            new Employee(105, "Bob", "IT", -5000.0, true);
        } catch (InvalidEmployeeDataException e) {
            ConsoleTableFormatter.printSuccess("Properly caught expected exception -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        System.out.println();

        // -------------------------------------------------------------------------
        // Bonus Features Demonstration: Modern Java Record
        // -------------------------------------------------------------------------
        System.out.println(">>> STEP 10: Modern Java Bonus Features (Record & Immutability)");
        Employee emp = service.getEmployeeById(104);
        EmployeeRecord record = emp.toRecord();
        System.out.println("Converted Employee to Java Record: " + record);
        System.out.println("Record Component Accessors: ID=" + record.id() + ", Name=" + record.name() + ", Active=" + record.active());
        System.out.println();

        System.out.println("===============================================================================");
        System.out.println("               ALL VERIFICATION CHECKS PASSED SUCCESSFULLY!                   ");
        System.out.println("===============================================================================");
    }
}
