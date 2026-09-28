package com.nashtech.redisapp.config;

import com.nashtech.redisapp.entity.EmployeeEntity;
import com.nashtech.redisapp.repository.EmployeeJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Preloads the database with sample employees specified in the assignment upon application startup.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final EmployeeJpaRepository employeeRepository;

    public DataInitializer(EmployeeJpaRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) {
        if (employeeRepository.count() == 0) {
            log.info("==> [DATA INITIALIZER] Seeding database with initial sample employees...");

            List<EmployeeEntity> initialEmployees = List.of(
                    new EmployeeEntity(101, "Alex", "Engineering", 90000.0, true),
                    new EmployeeEntity(102, "Sam", "Engineering", 125000.0, true),
                    new EmployeeEntity(103, "John", "Finance", 140000.0, false),
                    new EmployeeEntity(104, "Priya", "Engineering", 150000.0, true)
            );

            employeeRepository.saveAll(initialEmployees);
            log.info("==> [DATA INITIALIZER] Seeded {} employees into H2 database successfully.", initialEmployees.size());
        }
    }
}

