package com.nashtech.employeemanagement.util;

import java.util.Scanner;

/**
 * Robust input reader utility that encapsulates Scanner handling,
 * prevents trailing newline issues, and performs defensive validation.
 */
public class ConsoleInputReader {

    private final Scanner scanner;

    public ConsoleInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Reads a required non-empty string prompt.
     *
     * @param prompt text to display to user
     * @return validated non-empty string
     */
    public String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("[!] Input cannot be blank. Please try again.");
        }
    }

    /**
     * Reads a positive integer (e.g. for ID).
     *
     * @param prompt text to display to user
     * @return positive integer
     */
    public int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("[!] Value must be greater than zero. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid number format. Please enter an integer.");
            }
        }
    }

    /**
     * Reads an integer within a specified range [min, max].
     *
     * @param prompt text to display to user
     * @param min    minimum allowable value
     * @param max    maximum allowable value
     * @return integer in range
     */
    public int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("[!] Please enter a number between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("[!] Invalid input. Please enter a valid number between %d and %d.%n", min, max);
            }
        }
    }

    /**
     * Reads a non-negative double (e.g. for Salary).
     *
     * @param prompt text to display to user
     * @return non-negative double
     */
    public double readNonNegativeDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value >= 0.0) {
                    return value;
                }
                System.out.println("[!] Value cannot be negative. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid numeric format. Please enter a valid decimal number.");
            }
        }
    }

    /**
     * Reads a boolean status (Active / Inactive) with flexible input (yes/no, y/n, active/inactive, 1/0, true/false).
     *
     * @param prompt text to display to user
     * @return true for Active, false for Inactive
     */
    public boolean readBooleanStatus(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("active") || input.equals("a") || input.equals("yes") || input.equals("y") || input.equals("true") || input.equals("1")) {
                return true;
            }
            if (input.equals("inactive") || input.equals("i") || input.equals("no") || input.equals("n") || input.equals("false") || input.equals("0")) {
                return false;
            }
            System.out.println("[!] Invalid status. Please enter 'Active' (or A/Y) or 'Inactive' (or I/N).");
        }
    }
}
