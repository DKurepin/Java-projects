package com.kurepin.lab3.controller;

import com.kurepin.lab3.dto.EmployeeDto;
import com.kurepin.lab3.dto.TaskDto;
import com.kurepin.lab3.entities.Employee;
import com.kurepin.lab3.entities.Task;
import com.kurepin.lab3.mapper.EmployeeMapper;
import com.kurepin.lab3.mapper.TaskMapper;
import com.kurepin.lab3.services.EmployeeService;
import com.kurepin.lab3.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get_all_tasks")
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/get_task_by_id/{id}")
    public TaskDto getTaskById(@PathVariable("id") Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/get_all_tasks_by_name/{name}")
    public List<TaskDto> getAllTasksByName(@PathVariable("name") String name) {
        return taskService.getAllTasksByName(name);
    }

    @GetMapping("/get_all_tasks_by_employee_id/{id}")
    public List<TaskDto> getAllTasksByEmployeeId(@PathVariable("id") Long id) throws ParseException {
        EmployeeDto employeeDTO = employeeService.getEmployeeById(id);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        return taskService.getAllTasksByEmployeeId(employee);
    }

    @PostMapping("/add_task")
    public Task addTask(@RequestBody TaskDto taskDTO) throws ParseException {
        EmployeeDto employeeDTO = employeeService.getEmployeeById(taskDTO.getEmployeeId());
        Employee employee = employeeMapper.toEntity(employeeDTO);
        Task task = taskMapper.toEntity(taskDTO, employee);
        return taskService.saveTask(task);
    }

    @DeleteMapping("/delete_task_by_id/{id}")
    public void deleteTaskById(@PathVariable("id") Long id) {
        taskService.deleteByTaskId(id);
    }

    @DeleteMapping("/delete_task")
    public void deleteTask(@RequestBody TaskDto taskDTO) throws ParseException {
        EmployeeDto employeeDTO = employeeService.getEmployeeById(taskDTO.getEmployeeId());
        Employee employee = employeeMapper.toEntity(employeeDTO);
        Task task = taskMapper.toEntity(taskDTO, employee);
        taskService.deleteByTask(task);
    }

    @DeleteMapping("/delete_all_tasks")
    public void deleteAllTasks() {
        taskService.deleteAllTasks();
    }

    @PutMapping("/update_task/{id}")
    public Task updateTask(@RequestBody TaskDto taskDTO, @PathVariable("id") Long id) throws ParseException {
        EmployeeDto employeeDTO = employeeService.getEmployeeById(taskDTO.getEmployeeId());
        Employee employee = employeeMapper.toEntity(employeeDTO);
        Task task = taskMapper.toEntity(taskDTO, employee);
        return taskService.updateTask(task);
    }
}
