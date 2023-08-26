package com.kurepin.lab4.services;

import com.kurepin.lab4.dto.TaskDto;
import com.kurepin.lab4.entities.Employee;
import com.kurepin.lab4.entities.Task;

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
