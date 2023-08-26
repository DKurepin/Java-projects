package com.kurepin.service;


import com.kurepin.entities.dto.EmployeeDto;
import com.kurepin.entities.dto.TaskDto;

import java.text.ParseException;
import java.util.List;

public interface TaskService {
    TaskDto saveTask(TaskDto taskDto) throws ParseException;
    void deleteByTaskId(Long id);
    void deleteAllTasks();
    TaskDto updateTask(TaskDto taskDto) throws ParseException;
    TaskDto getTaskById(Long id);
    List<TaskDto> getAllTasks();
    List<TaskDto> getAllTasksByName(String name);
    List<TaskDto> getAllTasksByEmployeeId(EmployeeDto employeeDto) throws ParseException;
}
