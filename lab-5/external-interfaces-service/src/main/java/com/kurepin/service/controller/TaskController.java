package com.kurepin.service.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurepin.entities.dto.EmployeeDto;
import com.kurepin.entities.dto.TaskDto;
import com.kurepin.service.security.services.AccessControlSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
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
@RequestMapping("/tasks")
@EnableRabbit
public class TaskController {

    private final AccessControlSecurity accessControlSecurity;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/get_all_tasks")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        if (accessControlSecurity.isAdmin(username())) {
            rabbitTemplate.convertAndSend("task-exchange", "task.get.all", "");
            Message resultMessage = rabbitTemplate.receive("task-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                List<TaskDto> resultDtoList = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<List<TaskDto>>() {
                        });
                return ResponseEntity.ok(resultDtoList);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_task_by_id/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable("id") Long id) {
        if (accessControlSecurity.taskIsAllowed(username(), id)) {
            rabbitTemplate.convertAndSend("task-exchange", "task.get.byId", id);
            Message resultMessage = rabbitTemplate.receive("task-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                TaskDto taskDto = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<TaskDto>() {
                        });
                return ResponseEntity.ok(taskDto);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_all_tasks_by_name/{name}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<TaskDto>> getAllTasksByName(@PathVariable("name") String name) {
        if (accessControlSecurity.isAdmin(username())) {
            rabbitTemplate.convertAndSend("task-exchange", "task.get.allByName", name);
            Message resultMessage = rabbitTemplate.receive("task-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                List<TaskDto> resultDtoList = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<List<TaskDto>>() {
                        });
                return ResponseEntity.ok(resultDtoList);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_all_tasks_by_employee_id/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<TaskDto>> getAllTasksByEmployeeId(@PathVariable("id") Long id) {
        if (accessControlSecurity.taskIsAccessedByEmployeeId(username(), id)) {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setId(id);
            rabbitTemplate.convertAndSend("task-exchange", "task.get.allByEmployeeId", employeeDto);
            Message resultMessage = rabbitTemplate.receive("task-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                List<TaskDto> resultDtoList = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<List<TaskDto>>() {
                        });
                return ResponseEntity.ok(resultDtoList);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @PostMapping("/add_task")
    @PreAuthorize("hasAuthority('update')")
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto) throws ParseException {
        if (accessControlSecurity.taskIsAccessedByEmployeeId(username(), taskDto.getEmployeeId())) {
            rabbitTemplate.convertAndSend("task-exchange", "task.create", taskDto);
            Message resultMessage = rabbitTemplate.receive("task-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                TaskDto taskDtoResult = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<TaskDto>() {
                        });
                return ResponseEntity.ok(taskDtoResult);
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize result message", e);
            }
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_task_by_id/{id}")
    @PreAuthorize("hasAuthority('update')")
    public void deleteTaskById(@PathVariable("id") Long id) {
        if (accessControlSecurity.taskIsAllowed(username(), id)) {
            rabbitTemplate.convertAndSend("task-exchange", "task.delete.byId", id);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_all_tasks")
    @PreAuthorize("hasAuthority('update')")
    public void deleteAllTasks() {
        if (accessControlSecurity.isAdmin(username())) {
            rabbitTemplate.convertAndSend("task-exchange", "task.delete.all", "");
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @PutMapping("/update_task/{id}")
    @PreAuthorize("hasAuthority('update')")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto, @PathVariable("id") Long id) throws ParseException {
        if (accessControlSecurity.taskIsAllowed(username(), id)) {
            rabbitTemplate.convertAndSend("task-exchange", "task.update", taskDto);
            Message resultMessage = rabbitTemplate.receive("task-get-result-queue", 5000);
            if (resultMessage == null) {
                throw new RuntimeException("Failed to receive result message");
            }
            try {
                TaskDto taskDtoResult = objectMapper.readValue(resultMessage.getBody(),
                        new TypeReference<TaskDto>() {
                        });
                return ResponseEntity.ok(taskDtoResult);
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
