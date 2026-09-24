package com.nashtech.employeemanagement.repository;

import com.nashtech.employeemanagement.exception.DuplicateEmployeeException;
import com.nashtech.employeemanagement.model.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * In-memory implementation of EmployeeRepository using Java Collections Framework.
 * Backed by a LinkedHashMap to provide O(1) key lookups while preserving insertion order.
 */
public class InMemoryEmployeeRepository implements EmployeeRepository {

    private final Map<Integer, Employee> employeeStorage = new LinkedHashMap<>();

    @Override
    public synchronized Employee save(Employee employee) {
        Objects.requireNonNull(employee, "Employee cannot be null");
        if (employeeStorage.containsKey(employee.getId())) {
            throw new DuplicateEmployeeException(employee.getId());
        }
        employeeStorage.put(employee.getId(), employee);
        return employee;
    }

    @Override
    public Optional<Employee> findById(int id) {
        return Optional.ofNullable(employeeStorage.get(id));
    }

    @Override
    public List<Employee> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(employeeStorage.values()));
    }

    @Override
    public boolean existsById(int id) {
        return employeeStorage.containsKey(id);
    }

    @Override
    public synchronized boolean deleteById(int id) {
        return employeeStorage.remove(id) != null;
    }

    @Override
    public int count() {
        return employeeStorage.size();
    }

    @Override
    public synchronized void clear() {
        employeeStorage.clear();
    }
}
