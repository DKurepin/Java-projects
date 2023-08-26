package com.kurepin.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.kurepin.entities", "com.kurepin.service.security.models"})
public class ExternalServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExternalServiceApplication.class, args);
    }
}