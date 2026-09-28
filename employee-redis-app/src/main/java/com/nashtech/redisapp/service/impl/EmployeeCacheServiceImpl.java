package com.nashtech.redisapp.service.impl;

import com.nashtech.library.dto.EmployeeRequestDto;
import com.nashtech.library.dto.EmployeeResponseDto;
import com.nashtech.library.exception.DuplicateEmployeeException;
import com.nashtech.library.exception.EmployeeNotFoundException;
import com.nashtech.library.exception.InvalidEmployeeDataException;
import com.nashtech.redisapp.entity.EmployeeEntity;
import com.nashtech.redisapp.repository.EmployeeJpaRepository;
import com.nashtech.redisapp.service.EmployeeCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service implementation providing Employee business logic with Redis Caching.
 * Demonstrates:
 * - @Cacheable (Read from cache, fallback to database)
 * - @CachePut (Update database and synchronize cache)
 * - @CacheEvict (Delete from database and evict key from cache)
 */
@Service
public class EmployeeCacheServiceImpl implements EmployeeCacheService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeCacheServiceImpl.class);
    private static final String CACHE_NAME = "employees";

    private final EmployeeJpaRepository employeeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public EmployeeCacheServiceImpl(EmployeeJpaRepository employeeRepository,
                                    RedisTemplate<String, Object> redisTemplate) {
        this.employeeRepository = employeeRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * Requirement 9: Read operation with @Cacheable.
     * When called:
     * - Cache Miss: Method executes, database is queried, result is written to Redis.
     * - Cache Hit: Method is bypassed entirely, result is returned directly from Redis!
     */
    @Override
    @Cacheable(value = CACHE_NAME, key = "#id")
    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeById(int id) {
        log.info("==> [DATABASE HIT] Fetching employee from DB for ID: {}", id);
        return employeeRepository.findById(id)
                .map(EmployeeEntity::toResponseDto)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {
        if (requestDto == null) {
            throw new InvalidEmployeeDataException("Employee request payload cannot be null.");
        }
        if (requestDto.getId() == null || requestDto.getId() <= 0) {
            throw new InvalidEmployeeDataException("Employee ID must be a positive integer.");
        }
        if (employeeRepository.existsById(requestDto.getId())) {
            throw new DuplicateEmployeeException(requestDto.getId());
        }

        EmployeeEntity entity = new EmployeeEntity(
                requestDto.getId(),
                requestDto.getName(),
                requestDto.getDepartment(),
                requestDto.getSalary(),
                requestDto.getActive() != null ? requestDto.getActive() : true
        );

        EmployeeEntity saved = employeeRepository.save(entity);
        log.info("==> [DB SAVE] Created employee with ID: {}", saved.getId());
        return saved.toResponseDto();
    }

    /**
     * Requirement 10: Update operation with @CachePut.
     * Always executes method to update the database AND updates the Redis cache entry!
     */
    @Override
    @CachePut(value = CACHE_NAME, key = "#id")
    @Transactional
    public EmployeeResponseDto updateEmployee(int id, EmployeeRequestDto requestDto) {
        log.info("==> [CACHE PUT] Updating DB and refreshing Redis cache for ID: {}", id);
        EmployeeEntity existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        if (requestDto.getName() != null && !requestDto.getName().isBlank()) {
            existing.setName(requestDto.getName().trim());
        }
        if (requestDto.getDepartment() != null && !requestDto.getDepartment().isBlank()) {
            existing.setDepartment(requestDto.getDepartment().trim());
        }
        if (requestDto.getSalary() != null) {
            if (requestDto.getSalary() < 0) {
                throw new InvalidEmployeeDataException("Salary cannot be negative: " + requestDto.getSalary());
            }
            existing.setSalary(requestDto.getSalary());
        }
        if (requestDto.getActive() != null) {
            existing.setActive(requestDto.getActive());
        }

        EmployeeEntity updated = employeeRepository.save(existing);
        return updated.toResponseDto();
    }

    /**
     * Requirement 11: Delete operation with @CacheEvict.
     * Deletes employee from DB and removes the corresponding key from Redis cache.
     */
    @Override
    @CacheEvict(value = CACHE_NAME, key = "#id")
    @Transactional
    public void deleteEmployee(int id) {
        log.info("==> [CACHE EVICT] Deleting from DB and evicting Redis cache for ID: {}", id);
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeEntity::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getEmployeesByDepartment(String department) {
        if (department == null || department.isBlank()) {
            throw new InvalidEmployeeDataException("Department cannot be blank.");
        }
        return employeeRepository.findByDepartmentIgnoreCase(department.trim()).stream()
                .map(EmployeeEntity::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getActiveEmployeesWithSalaryGreaterThan(double minSalary) {
        if (minSalary < 0) {
            throw new InvalidEmployeeDataException("Salary threshold cannot be negative: " + minSalary);
        }
        // Stream-based active employee filtering matching assignment specification
        return employeeRepository.findByActiveTrueAndSalaryGreaterThan(minSalary).stream()
                .map(EmployeeEntity::toResponseDto)
                .toList();
    }

    @Override
    public List<String> getActiveCacheKeys() {
        try {
            Set<String> keys = redisTemplate.keys(CACHE_NAME + "*");
            return keys != null ? new ArrayList<>(keys) : List.of();
        } catch (Exception e) {
            log.warn("Could not query Redis cache keys: {}", e.getMessage());
            return List.of();
        }
    }
}

