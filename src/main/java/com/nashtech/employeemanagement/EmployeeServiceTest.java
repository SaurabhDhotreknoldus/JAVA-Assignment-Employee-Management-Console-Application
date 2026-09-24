package com.nashtech.employeemanagement;

import com.nashtech.employeemanagement.exception.DuplicateEmployeeException;
import com.nashtech.employeemanagement.exception.EmployeeNotFoundException;
import com.nashtech.employeemanagement.exception.InvalidEmployeeDataException;
import com.nashtech.employeemanagement.model.Employee;
import com.nashtech.employeemanagement.repository.InMemoryEmployeeRepository;
import com.nashtech.employeemanagement.service.EmployeeService;
import com.nashtech.employeemanagement.service.EmployeeServiceImpl;

import java.util.List;

/**
 * Self-contained Unit and Integration Test Suite.
 * Runs without requiring external testing framework JARs on the classpath,
 * making it 100% portable for command-line evaluation and CI/CD.
 */
public class EmployeeServiceTest {

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("===============================================================================");
        System.out.println("                 RUNNING EMPLOYEE SERVICE UNIT TESTS                           ");
        System.out.println("===============================================================================");

        runTest("testAddAndRetrieveEmployee", EmployeeServiceTest::testAddAndRetrieveEmployee);
        runTest("testSearchNonExistentEmployeeThrowsException", EmployeeServiceTest::testSearchNonExistentEmployeeThrowsException);
        runTest("testDuplicateEmployeeThrowsException", EmployeeServiceTest::testDuplicateEmployeeThrowsException);
        runTest("testFilterByDepartment", EmployeeServiceTest::testFilterByDepartment);
        runTest("testFilterActiveEmployeesWithSalaryThreshold", EmployeeServiceTest::testFilterActiveEmployeesWithSalaryThreshold);
        runTest("testInvalidEmployeeDataValidations", EmployeeServiceTest::testInvalidEmployeeDataValidations);

        System.out.println("-------------------------------------------------------------------------------");
        System.out.printf("Test Summary: %d Passed, %d Failed%n", testsPassed, testsFailed);
        System.out.println("===============================================================================");

        if (testsFailed > 0) {
            System.exit(1);
        }
    }

    private static void runTest(String testName, Runnable test) {
        try {
            test.run();
            System.out.println("[PASS] " + testName);
            testsPassed++;
        } catch (Throwable t) {
            System.err.println("[FAIL] " + testName + " -> " + t.getMessage());
            testsFailed++;
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new AssertionError(String.format("%s [Expected: %s, Actual: %s]", message, expected, actual));
    }

    private static void testAddAndRetrieveEmployee() {
        EmployeeService service = new EmployeeServiceImpl(new InMemoryEmployeeRepository());
        Employee emp = new Employee(201, "Alice", "Security", 110000.0, true);
        service.addEmployee(emp);

        Employee retrieved = service.getEmployeeById(201);
        assertEquals(201, retrieved.getId(), "Employee ID mismatch");
        assertEquals("Alice", retrieved.getName(), "Employee name mismatch");
        assertEquals("Security", retrieved.getDepartment(), "Employee department mismatch");
        assertEquals(110000.0, retrieved.getSalary(), "Employee salary mismatch");
        assertTrue(retrieved.isActive(), "Employee active status mismatch");
    }

    private static void testSearchNonExistentEmployeeThrowsException() {
        EmployeeService service = new EmployeeServiceImpl(new InMemoryEmployeeRepository());
        boolean exceptionThrown = false;
        try {
            service.getEmployeeById(999);
        } catch (EmployeeNotFoundException e) {
            exceptionThrown = true;
            assertEquals(999, (int) e.getEmployeeId(), "Exception employeeId mismatch");
        }
        assertTrue(exceptionThrown, "EmployeeNotFoundException should be thrown for missing employee ID");
    }

    private static void testDuplicateEmployeeThrowsException() {
        EmployeeService service = new EmployeeServiceImpl(new InMemoryEmployeeRepository());
        service.addEmployee(new Employee(101, "Alex", "Engineering", 90000.0, true));

        boolean duplicateCaught = false;
        try {
            service.addEmployee(new Employee(101, "Duplicate Alex", "Engineering", 95000.0, true));
        } catch (DuplicateEmployeeException e) {
            duplicateCaught = true;
            assertEquals(101, e.getEmployeeId(), "DuplicateEmployeeException ID mismatch");
        }
        assertTrue(duplicateCaught, "DuplicateEmployeeException should be thrown when adding existing ID");
    }

    private static void testFilterByDepartment() {
        EmployeeService service = new EmployeeServiceImpl(new InMemoryEmployeeRepository());
        service.loadSampleEmployees();

        List<Employee> eng = service.getEmployeesByDepartment("Engineering");
        assertEquals(3, eng.size(), "Engineering department should contain 3 employees (Alex, Sam, Priya)");

        List<Employee> fin = service.getEmployeesByDepartment("Finance");
        assertEquals(1, fin.size(), "Finance department should contain 1 employee (John)");

        List<Employee> hr = service.getEmployeesByDepartment("HR");
        assertEquals(0, hr.size(), "HR department should return empty list");
    }

    private static void testFilterActiveEmployeesWithSalaryThreshold() {
        EmployeeService service = new EmployeeServiceImpl(new InMemoryEmployeeRepository());
        service.loadSampleEmployees();

        // Sample dataset:
        // 101 | Alex  | Engineering | 90000  | Active
        // 102 | Sam   | Engineering | 125000 | Active
        // 103 | John  | Finance     | 140000 | Inactive
        // 104 | Priya | Engineering | 150000 | Active

        // Filter active employees with salary > 100,000 -> Expected: Sam (125000) and Priya (150000)
        List<Employee> highEarners = service.getActiveEmployeesWithSalaryGreaterThan(100000.0);
        assertEquals(2, highEarners.size(), "Should have exactly 2 active employees earning > 100000");

        List<String> names = highEarners.stream().map(Employee::getName).toList();
        assertTrue(names.contains("Sam"), "Sam should be present");
        assertTrue(names.contains("Priya"), "Priya should be present");
        assertTrue(!names.contains("John"), "John is inactive and should not be included");
        assertTrue(!names.contains("Alex"), "Alex earns 90000 <= 100000 and should not be included");
    }

    private static void testInvalidEmployeeDataValidations() {
        // Test negative ID
        try {
            new Employee(-1, "Test", "Dept", 50000.0, true);
            throw new AssertionError("Should reject negative ID");
        } catch (InvalidEmployeeDataException expected) {
            // Success
        }

        // Test blank name
        try {
            new Employee(1, "   ", "Dept", 50000.0, true);
            throw new AssertionError("Should reject blank name");
        } catch (InvalidEmployeeDataException expected) {
            // Success
        }

        // Test negative salary
        try {
            new Employee(1, "Test", "Dept", -10.0, true);
            throw new AssertionError("Should reject negative salary");
        } catch (InvalidEmployeeDataException expected) {
            // Success
        }
    }
}
