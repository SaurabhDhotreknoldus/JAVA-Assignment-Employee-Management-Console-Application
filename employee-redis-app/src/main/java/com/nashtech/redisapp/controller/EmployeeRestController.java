package com.nashtech.redisapp.controller;

import com.nashtech.library.dto.EmployeeRequestDto;
import com.nashtech.library.dto.EmployeeResponseDto;
import com.nashtech.redisapp.service.EmployeeCacheService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller exposing Employee CRUD endpoints with Redis caching.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    private final EmployeeCacheService employeeService;

    public EmployeeRestController(EmployeeCacheService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Requirement 6: Create new employee.
     * POST /api/employees
     */
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeRequestDto requestDto) {
        EmployeeResponseDto created = employeeService.createEmployee(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Requirement 6 & 9: Read employee by ID with @Cacheable.
     * GET /api/employees/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable int id) {
        EmployeeResponseDto employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    /**
     * Requirement 6 & 10: Update employee with @CachePut.
     * PUT /api/employees/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable int id,
                                                             @RequestBody EmployeeRequestDto requestDto) {
        EmployeeResponseDto updated = employeeService.updateEmployee(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Requirement 6 & 11: Delete employee with @CacheEvict.
     * DELETE /api/employees/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all employees.
     * GET /api/employees
     */
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    /**
     * Filter employees by department.
     * GET /api/employees/department/{department}
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(department));
    }

    /**
     * Filter active employees earning greater than minimum salary.
     * GET /api/employees/active/salary-threshold?minSalary=100000
     */
    @GetMapping("/active/salary-threshold")
    public ResponseEntity<List<EmployeeResponseDto>> getActiveEmployeesBySalary(
            @RequestParam(defaultValue = "100000") double minSalary) {
        return ResponseEntity.ok(employeeService.getActiveEmployeesWithSalaryGreaterThan(minSalary));
    }

    /**
     * Helper endpoint for demo/evaluation: inspects active Redis cache keys.
     * GET /api/employees/cache/keys
     */
    @GetMapping("/cache/keys")
    public ResponseEntity<List<String>> getCacheKeys() {
        return ResponseEntity.ok(employeeService.getActiveCacheKeys());
    }
}

