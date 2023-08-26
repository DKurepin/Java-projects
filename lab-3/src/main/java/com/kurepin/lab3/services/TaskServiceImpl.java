package com.kurepin.lab3.services;

import com.kurepin.lab3.dto.TaskDto;
import com.kurepin.lab3.entities.Employee;
import com.kurepin.lab3.entities.Task;
import com.kurepin.lab3.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService{

    @Autowired
    private final TaskRepository taskRepository;

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteByTaskId(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteByTask(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.getReferenceById(id);
        TaskDto taskDTO = TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .deadline(task.getDeadline())
                .description(task.getDescription())
                .type(task.getType())
                .author(task.getAuthor())
                .employeeId(task.getEmployeeId().getId())
                .build();
        return taskDTO;
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
    public List<TaskDto> getAllTasksByEmployeeId(Employee employeeId) {
        List<Task> tasks = taskRepository.getAllTasksByEmployeeId(employeeId);
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
}
