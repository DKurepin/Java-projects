package com.kurepin.lab4.controller;

import com.kurepin.lab4.dto.EmployeeDto;
import com.kurepin.lab4.dto.TaskDto;
import com.kurepin.lab4.entities.Employee;
import com.kurepin.lab4.entities.Task;
import com.kurepin.lab4.mapper.EmployeeMapper;
import com.kurepin.lab4.mapper.TaskMapper;
import com.kurepin.lab4.security.services.AccessControlSecurity;
import com.kurepin.lab4.services.EmployeeService;
import com.kurepin.lab4.services.TaskService;
import lombok.RequiredArgsConstructor;
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

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final EmployeeService employeeService;
    private final TaskMapper taskMapper;
    private final EmployeeMapper employeeMapper;
    private final AccessControlSecurity accessControlSecurity;


    @GetMapping("/get_all_tasks")
    @PreAuthorize("hasAuthority('read')")
    public List<TaskDto> getAllTasks() {
        if (accessControlSecurity.isAdmin(username())) {
            return taskService.getAllTasks();
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_task_by_id/{id}")
    @PreAuthorize("hasAuthority('read')")
    public TaskDto getTaskById(@PathVariable("id") Long id) {
        if (accessControlSecurity.taskIsAllowed(username(), id)) {
            return taskService.getTaskById(id);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_all_tasks_by_name/{name}")
    @PreAuthorize("hasAuthority('read')")
    public List<TaskDto> getAllTasksByName(@PathVariable("name") String name) {
        if (accessControlSecurity.isAdmin(username())) {
            return taskService.getAllTasksByName(name);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_all_tasks_by_employee_id/{id}")
    @PreAuthorize("hasAuthority('read')")
    public List<TaskDto> getAllTasksByEmployeeId(@PathVariable("id") Long id) throws ParseException {
        if (accessControlSecurity.taskIsAccessedByEmployeeId(username(), id)) {
            EmployeeDto employeeDTO = employeeService.getEmployeeById(id);
            Employee employee = employeeMapper.toEntity(employeeDTO);
            return taskService.getAllTasksByEmployeeId(employee);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @PostMapping("/add_task")
    @PreAuthorize("hasAuthority('update')")
    public Task addTask(@RequestBody TaskDto taskDTO) throws ParseException {
        if (accessControlSecurity.taskIsAccessedByEmployeeId(username(), taskDTO.getEmployeeId())) {
            EmployeeDto employeeDTO = employeeService.getEmployeeById(taskDTO.getEmployeeId());
            Employee employee = employeeMapper.toEntity(employeeDTO);
            Task task = taskMapper.toEntity(taskDTO, employee);
            return taskService.saveTask(task);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_task_by_id/{id}")
    @PreAuthorize("hasAuthority('update')")
    public void deleteTaskById(@PathVariable("id") Long id) {
        if (accessControlSecurity.taskIsAllowed(username(), id)) {
            taskService.deleteByTaskId(id);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_task")
    @PreAuthorize("hasAuthority('update')")
    public void deleteTask(@RequestBody TaskDto taskDTO) throws ParseException {
        if (accessControlSecurity.taskIsAccessedByEmployeeId(username(), taskDTO.getEmployeeId())) {
            EmployeeDto employeeDTO = employeeService.getEmployeeById(taskDTO.getEmployeeId());
            Employee employee = employeeMapper.toEntity(employeeDTO);
            Task task = taskMapper.toEntity(taskDTO, employee);
            taskService.deleteByTask(task);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_all_tasks")
    @PreAuthorize("hasAuthority('update')")
    public void deleteAllTasks() {
        if (accessControlSecurity.isAdmin(username())) {
            taskService.deleteAllTasks();
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @PutMapping("/update_task/{id}")
    @PreAuthorize("hasAuthority('update')")
    public Task updateTask(@RequestBody TaskDto taskDTO, @PathVariable("id") Long id) throws ParseException {
        if (accessControlSecurity.taskIsAllowed(username(), id)) {
            EmployeeDto employeeDTO = employeeService.getEmployeeById(taskDTO.getEmployeeId());
            Employee employee = employeeMapper.toEntity(employeeDTO);
            Task task = taskMapper.toEntity(taskDTO, employee);
            return taskService.updateTask(task);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    private String username() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
