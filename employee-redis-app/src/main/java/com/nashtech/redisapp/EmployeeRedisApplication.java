package com.nashtech.redisapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main Spring Boot Application for Employee Management with Redis Caching.
 * Demonstrates:
 * - Artifact consumption from Nexus (Employee Library)
 * - Spring Data JPA Database Persistence
 * - Spring Cache abstraction with Redis CacheManager
 * - CRUD REST APIs with @Cacheable, @CachePut, and @CacheEvict
 */
@SpringBootApplication
@EnableCaching
public class EmployeeRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeRedisApplication.class, args);
    }
}

