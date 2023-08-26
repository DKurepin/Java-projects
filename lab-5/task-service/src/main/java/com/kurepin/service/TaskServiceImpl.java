package com.kurepin.service;


import com.kurepin.entities.Employee;
import com.kurepin.entities.Task;
import com.kurepin.entities.dto.EmployeeDto;
import com.kurepin.entities.dto.TaskDto;
import com.kurepin.entities.mappers.EmployeeMapper;
import com.kurepin.entities.mappers.TaskMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@ComponentScan(basePackages = "com.kurepin.entities.mappers")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final EmployeeMapper employeeMapper;

    @Override
    public TaskDto saveTask(TaskDto taskDto) throws ParseException {
        Task task = taskMapper.toEntity(taskDto);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public void deleteByTaskId(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto) throws ParseException {
        Task task = taskMapper.toEntity(taskDto);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        TaskDto taskDto = taskMapper.toDto(task.get());
        return taskDto;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDto> taskDtos = tasks.stream().map(task -> TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .deadline(task.getDeadline())
                .description(task.getDescription())
                .type(task.getType())
                .author(task.getAuthor())
                .employeeId(task.getEmployeeId().getId())
                .build()).toList();
        return taskDtos;
    }

    @Override
    public List<TaskDto> getAllTasksByName(String name) {
        List<Task> tasks = taskRepository.getAllByName(name);
        List<TaskDto> taskDtoList = tasks.stream().map(task -> TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .deadline(task.getDeadline())
                .description(task.getDescription())
                .type(task.getType())
                .author(task.getAuthor())
                .employeeId(task.getEmployeeId().getId())
                .build()).toList();
        return taskDtoList;
    }

    @Override
    public List<TaskDto> getAllTasksByEmployeeId(EmployeeDto employeeDto) throws ParseException {
        Employee employee = employeeMapper.toEntity(employeeDto);
        List<Task> tasks = taskRepository.getAllTasksByEmployeeId(employee);
        List<TaskDto> taskDtoList = tasks.stream().map(task1 -> TaskDto.builder()
                .id(task1.getId())
                .name(task1.getName())
                .deadline(task1.getDeadline())
                .description(task1.getDescription())
                .type(task1.getType())
                .author(task1.getAuthor())
                .employeeId(task1.getEmployeeId().getId())
                .build()).toList();
        return taskDtoList;
    }
}
