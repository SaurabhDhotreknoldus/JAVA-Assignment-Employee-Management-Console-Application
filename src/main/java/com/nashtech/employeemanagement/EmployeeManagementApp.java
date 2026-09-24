package com.nashtech.employeemanagement;

import com.nashtech.employeemanagement.exception.DuplicateEmployeeException;
import com.nashtech.employeemanagement.exception.EmployeeNotFoundException;
import com.nashtech.employeemanagement.exception.InvalidEmployeeDataException;
import com.nashtech.employeemanagement.model.Employee;
import com.nashtech.employeemanagement.model.EmployeeRecord;
import com.nashtech.employeemanagement.repository.InMemoryEmployeeRepository;
import com.nashtech.employeemanagement.service.EmployeeService;
import com.nashtech.employeemanagement.service.EmployeeServiceImpl;
import com.nashtech.employeemanagement.util.ConsoleInputReader;
import com.nashtech.employeemanagement.util.ConsoleTableFormatter;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * Main Entry Point and Console Controller for Employee Management Application.
 * <p>
 * Key Concepts Demonstrated:
 * 1. Java Fundamentals: Variables, Data types, Loops, Conditionals, Methods
 * 2. OOP: Classes, Encapsulation, Interface Abstraction, Dependency Injection
 * 3. Collections Framework: Map (LinkedHashMap) and List (ArrayList)
 * 4. Exception Handling: Custom EmployeeNotFoundException, DuplicateEmployeeException, try-catch-finally
 * 5. Java 8+ Features: Stream API, Method references, Lambdas
 * 6. Modern Java (Bonus): Switch expressions, Records, Optional
 */
public class EmployeeManagementApp {

    private final EmployeeService employeeService;
    private final ConsoleInputReader inputReader;

    public EmployeeManagementApp(EmployeeService employeeService, Scanner scanner) {
        this.employeeService = employeeService;
        this.inputReader = new ConsoleInputReader(scanner);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        InMemoryEmployeeRepository repository = new InMemoryEmployeeRepository();
        EmployeeService service = new EmployeeServiceImpl(repository);

        // Preload example employees specified in assignment instructions
        service.loadSampleEmployees();

        EmployeeManagementApp app = new EmployeeManagementApp(service, scanner);
        app.run();
    }

    /**
     * Main application loop.
     */
    public void run() {
        printBanner();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = inputReader.readIntInRange("Enter your choice (1-8): ", 1, 8);

            // Modern Java Feature: Switch Expression (Bonus)
            switch (choice) {
                case 1 -> handleAddEmployee();
                case 2 -> handleDisplayAllEmployees();
                case 3 -> handleSearchEmployeeById();
                case 4 -> handleDisplayEmployeesByDepartment();
                case 5 -> handleDisplayActiveEmployeesBySalary();
                case 6 -> handleDemonstrateModernRecord();
                case 7 -> handleReloadSampleData();
                case 8 -> {
                    System.out.println("\nThank you for using the Employee Management Application. Goodbye!\n");
                    running = false;
                }
                default -> ConsoleTableFormatter.printError("Invalid choice. Please select an option between 1 and 8.");
            }

            if (running) {
                System.out.println();
            }
        }
    }

    private void printBanner() {
        System.out.println("===============================================================================");
        System.out.println("         NASH TECH - JAVA ESSENTIALS: EMPLOYEE MANAGEMENT SYSTEM              ");
        System.out.println("===============================================================================");
        System.out.println("[INFO] Sample employees preloaded (Alex [101], Sam [102], John [103], Priya [104])");
        System.out.println();
    }

    private void printMenu() {
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("                             MAIN MENU                                         ");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println(" 1. Add Employee");
        System.out.println(" 2. Display All Employees");
        System.out.println(" 3. Search Employee by ID");
        System.out.println(" 4. Display Employees by Department");
        System.out.println(" 5. Display Active Employees with Salary > Threshold (Stream API Demo)");
        System.out.println(" 6. View Employee as Modern Java Record (Bonus Demonstration)");
        System.out.println(" 7. Reload / Reset Initial Sample Dataset");
        System.out.println(" 8. Exit");
        System.out.println("-------------------------------------------------------------------------------");
    }

    /**
     * Requirement 1: Add employees to the application.
     * Demonstrates exception handling for duplicates and invalid input.
     */
    private void handleAddEmployee() {
        System.out.println("\n--- [1] Add New Employee ---");
        try {
            int id = inputReader.readPositiveInt("Enter Employee ID: ");
            String name = inputReader.readNonEmptyString("Enter Name: ");
            String department = inputReader.readNonEmptyString("Enter Department: ");
            double salary = inputReader.readNonNegativeDouble("Enter Salary: ");
            boolean active = inputReader.readBooleanStatus("Enter Status (Active/Inactive): ");

            Employee newEmployee = new Employee(id, name, department, salary, active);
            employeeService.addEmployee(newEmployee);

            ConsoleTableFormatter.printSuccess("Employee added successfully!");
            ConsoleTableFormatter.printEmployeeCard(newEmployee);
        } catch (DuplicateEmployeeException e) {
            ConsoleTableFormatter.printError("Failed to add employee: " + e.getMessage());
        } catch (InvalidEmployeeDataException e) {
            ConsoleTableFormatter.printError("Validation failed: " + e.getMessage());
        } catch (Exception e) {
            ConsoleTableFormatter.printError("Unexpected error occurred while adding employee: " + e.getMessage());
        }
    }

    /**
     * Requirement 2: Display all employees.
     */
    private void handleDisplayAllEmployees() {
        System.out.println("\n--- [2] All Registered Employees ---");
        List<Employee> allEmployees = employeeService.getAllEmployees();
        ConsoleTableFormatter.printEmployeeTable(allEmployees);
    }

    /**
     * Requirement 3 & 6: Search for an employee using Employee ID.
     * Demonstrates handling scenario where an employee cannot be found using EmployeeNotFoundException.
     */
    private void handleSearchEmployeeById() {
        System.out.println("\n--- [3] Search Employee by ID ---");
        int id = inputReader.readPositiveInt("Enter Employee ID to search: ");

        try {
            Employee employee = employeeService.getEmployeeById(id);
            ConsoleTableFormatter.printSuccess("Employee found:");
            ConsoleTableFormatter.printEmployeeCard(employee);
        } catch (EmployeeNotFoundException e) {
            ConsoleTableFormatter.printError(e.getMessage());
        }
    }

    /**
     * Requirement 4: Display employees belonging to a specific department.
     */
    private void handleDisplayEmployeesByDepartment() {
        System.out.println("\n--- [4] Display Employees by Department ---");
        String department = inputReader.readNonEmptyString("Enter Department Name (e.g. Engineering, Finance): ");

        try {
            List<Employee> departmentEmployees = employeeService.getEmployeesByDepartment(department);
            if (departmentEmployees.isEmpty()) {
                ConsoleTableFormatter.printInfo("No employees found in department: '" + department + "'");
            } else {
                System.out.printf("%nEmployees in department '%s':%n", department);
                ConsoleTableFormatter.printEmployeeTable(departmentEmployees);
            }
        } catch (InvalidEmployeeDataException e) {
            ConsoleTableFormatter.printError("Validation error: " + e.getMessage());
        }
    }

    /**
     * Requirement 5: Display active employees whose salary is greater than a given value.
     * Demonstrates Stream API and Lambda expression filtering:
     * employees.stream()
     *          .filter(Employee::isActive)
     *          .filter(e -> e.getSalary() > threshold)
     *          .forEach(...)
     */
    private void handleDisplayActiveEmployeesBySalary() {
        System.out.println("\n--- [5] Filter Active Employees by Salary Threshold ---");
        double minSalary = inputReader.readNonNegativeDouble("Enter Minimum Salary Threshold (e.g. 100000): ");

        try {
            List<Employee> filtered = employeeService.getActiveEmployeesWithSalaryGreaterThan(minSalary);

            System.out.printf("%nActive employees earning more than %.2f:%n", minSalary);
            if (filtered.isEmpty()) {
                ConsoleTableFormatter.printInfo(String.format("No active employees found earning more than %.2f", minSalary));
            } else {
                ConsoleTableFormatter.printEmployeeTable(filtered);

                // Additional assignment example output demonstration:
                System.out.println("Names of matching employees (Stream extraction):");
                filtered.stream()
                        .map(Employee::getName)
                        .forEach(name -> System.out.println(" - " + name));
                System.out.println();
            }
        } catch (InvalidEmployeeDataException e) {
            ConsoleTableFormatter.printError("Validation error: " + e.getMessage());
        }
    }

    /**
     * Bonus Demonstration: View Employee using Modern Java Record.
     */
    private void handleDemonstrateModernRecord() {
        System.out.println("\n--- [6] Modern Java Feature: Record Demonstration ---");
        int id = inputReader.readPositiveInt("Enter Employee ID to view as Java Record: ");

        try {
            Employee employee = employeeService.getEmployeeById(id);
            EmployeeRecord record = employee.toRecord();

            System.out.println("\nJava 16+ Record Instance:");
            System.out.println(record);
            System.out.printf("Record Accessors -> id: %d, name: '%s', dept: '%s', salary: %.2f, active: %b%n",
                    record.id(), record.name(), record.department(), record.salary(), record.active());
        } catch (EmployeeNotFoundException e) {
            ConsoleTableFormatter.printError(e.getMessage());
        }
    }

    /**
     * Reload sample employees from initial test dataset.
     */
    private void handleReloadSampleData() {
        System.out.println("\n--- [7] Reload Initial Sample Dataset ---");
        employeeService.loadSampleEmployees();
        ConsoleTableFormatter.printSuccess("Sample employees reloaded successfully.");
        ConsoleTableFormatter.printEmployeeTable(employeeService.getAllEmployees());
    }
}
