package com.kurepin.lab3.services;

import com.kurepin.lab3.dto.TaskDto;
import com.kurepin.lab3.entities.Employee;
import com.kurepin.lab3.entities.Task;

import java.util.List;

public interface TaskService {
    Task saveTask(Task task);
    void deleteByTaskId(Long id);
    void deleteByTask(Task task);
    void deleteAllTasks();
    Task updateTask(Task task);
    TaskDto getTaskById(Long id);
    List<TaskDto> getAllTasks();
    List<TaskDto> getAllTasksByName(String name);
    List<TaskDto> getAllTasksByEmployeeId(Employee employeeId);
}
