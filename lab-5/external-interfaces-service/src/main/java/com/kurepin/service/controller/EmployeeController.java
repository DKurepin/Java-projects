package com.kurepin.service.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurepin.entities.dto.EmployeeDto;
import com.kurepin.service.security.services.AccessControlSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
@EnableRabbit
public class EmployeeController {
    private final AccessControlSecurity accessControlSecurity;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping("/get_all_employees")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        rabbitTemplate.convertAndSend("employee-exchange", "employee.get.all", "");
        Message resultMessage = rabbitTemplate.receive("employee-get-result-queue", 5000);
        if (resultMessage == null) {
            throw new RuntimeException("Failed to receive result message");
        }
        try {
            List<EmployeeDto> resultDtoList = objectMapper.readValue(resultMessage.getBody(),
                    new TypeReference<List<EmployeeDto>>() {
                    });
            return ResponseEntity.ok(resultDtoList);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize result message", e);
        }
    }


    @GetMapping("/get_employee_by_id/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long id) {
        if (accessControlSecurity.employeeIsAllowed(username(), id)) {
            rabbitTemplate.convertAndSend("employee-exchange", "employee.get.byId", id);
            Message resultMessage = rabbitTemplate.receive("employee-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                EmployeeDto employeeDto = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<EmployeeDto>() {
                        });
                return ResponseEntity.ok(employeeDto);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_all_employees_by_name/{name}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<EmployeeDto>> getAllEmployeesByName(@PathVariable("name") String name) {
        if (accessControlSecurity.isAdmin(username())) {
            rabbitTemplate.convertAndSend("employee-exchange", "employee.get.allByName", name);
            Message resultMessage = rabbitTemplate.receive("employee-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                List<EmployeeDto> resultDtoList = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<List<EmployeeDto>>() {
                        });
                return ResponseEntity.ok(resultDtoList);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @PostMapping("/add_employee")
    @PreAuthorize("hasAuthority('update')")
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) throws ParseException {
        if (accessControlSecurity.isAdmin(username())) {
            rabbitTemplate.convertAndSend("employee-exchange", "employee.create", employeeDto);
            Message resultMessage = rabbitTemplate.receive("employee-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                EmployeeDto employeeDtoResult = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<EmployeeDto>() {
                        });
                return ResponseEntity.ok(employeeDtoResult);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_employee_by_id/{id}")
    @PreAuthorize("hasAuthority('update')")
    public void deleteEmployeeById(@PathVariable Long id) {
        if (accessControlSecurity.employeeIsAllowed(username(), id)) {
            rabbitTemplate.convertAndSend("employee-exchange", "employee.delete.byId", id);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_employee")
    @PreAuthorize("hasAuthority('update')")
    public void deleteEmployee(@RequestBody EmployeeDto employeeDto) throws ParseException {
        if (accessControlSecurity.employeeIsAllowed(username(), employeeDto.getId())) {
            rabbitTemplate.convertAndSend("employee-exchange", "employee.delete.byEmployee", employeeDto);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_all_employees")
    @PreAuthorize("hasAuthority('write')")
    public void deleteAllEmployees() {
        if (accessControlSecurity.isAdmin(username())) {
            rabbitTemplate.convertAndSend("employee-exchange", "employee.delete.all", "");
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @PutMapping("/update_employee/{id}")
    @PreAuthorize("hasAuthority('update')")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable("id") Long id) throws ParseException {
        if (accessControlSecurity.employeeIsAllowed(username(), id)) {
            rabbitTemplate.convertAndSend("employee-exchange", "employee.update", employeeDto);
            Message resultMessage = rabbitTemplate.receive("employee-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                EmployeeDto employeeDtoResult = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<EmployeeDto>() {
                        });
                return ResponseEntity.ok(employeeDtoResult);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    private String username() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
