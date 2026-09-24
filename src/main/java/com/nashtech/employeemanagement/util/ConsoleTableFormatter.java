package com.nashtech.employeemanagement.util;

import com.nashtech.employeemanagement.model.Employee;

import java.util.List;

/**
 * Utility class to print formatted ASCII tables and alerts to the console.
 * Enhances readability and visual appeal for console users and assignment evaluation.
 */
public final class ConsoleTableFormatter {

    private ConsoleTableFormatter() {
        // Private constructor for utility class
    }

    private static final String TABLE_BORDER =
            "+------+----------------------+----------------------+-----------------+----------+";
    private static final String TABLE_HEADER =
            "| ID   | Name                 | Department           | Salary          | Status   |";

    /**
     * Prints a collection of employees in a structured ASCII table.
     *
     * @param employees list of employees to render
     */
    public static void printEmployeeTable(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            System.out.println("\n[!] No employee records to display.\n");
            return;
        }

        System.out.println(TABLE_BORDER);
        System.out.println(TABLE_HEADER);
        System.out.println(TABLE_BORDER);

        for (Employee emp : employees) {
            System.out.printf("| %-4d | %-20s | %-20s | %15.2f | %-8s |%n",
                    emp.getId(),
                    truncate(emp.getName(), 20),
                    truncate(emp.getDepartment(), 20),
                    emp.getSalary(),
                    emp.isActive() ? "Active" : "Inactive"
            );
        }
        System.out.println(TABLE_BORDER);
        System.out.printf("Total records: %d%n%n", employees.size());
    }

    /**
     * Prints a detailed card for a single employee.
     *
     * @param emp employee to display
     */
    public static void printEmployeeCard(Employee emp) {
        if (emp == null) return;
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|                   EMPLOYEE DETAILS                    |");
        System.out.println("+-------------------------------------------------------+");
        System.out.printf("| ID          : %-39d |%n", emp.getId());
        System.out.printf("| Name        : %-39s |%n", truncate(emp.getName(), 39));
        System.out.printf("| Department  : %-39s |%n", truncate(emp.getDepartment(), 39));
        System.out.printf("| Salary      : %-39.2f |%n", emp.getSalary());
        System.out.printf("| Status      : %-39s |%n", emp.isActive() ? "Active" : "Inactive");
        System.out.println("+-------------------------------------------------------+\n");
    }

    /**
     * Prints a success banner.
     *
     * @param message info message
     */
    public static void printSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    /**
     * Prints an error banner.
     *
     * @param message error message
     */
    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    /**
     * Prints an info banner.
     *
     * @param message info message
     */
    public static void printInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    private static String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}
