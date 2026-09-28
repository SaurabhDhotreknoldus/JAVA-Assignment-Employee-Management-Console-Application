package com.nashtech.library.util;

import com.nashtech.library.dto.EmployeeRequestDto;
import com.nashtech.library.dto.EmployeeResponseDto;
import com.nashtech.library.model.Employee;

/**
 * Utility mapper converting between Domain Model and DTO representations.
 */
public final class EmployeeMapper {

    private EmployeeMapper() {
    }

    public static EmployeeResponseDto toResponseDto(Employee employee) {
        if (employee == null) return null;
        return new EmployeeResponseDto(
                employee.getId(),
                employee.getName(),
                employee.getDepartment(),
                employee.getSalary(),
                employee.isActive()
        );
    }

    public static Employee toDomain(EmployeeRequestDto dto) {
        if (dto == null) return null;
        return new Employee(
                dto.getId() != null ? dto.getId() : 0,
                dto.getName(),
                dto.getDepartment(),
                dto.getSalary() != null ? dto.getSalary() : 0.0,
                dto.getActive() != null ? dto.getActive() : true
        );
    }
}

