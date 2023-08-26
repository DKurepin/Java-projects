package com.kurepin.lab3.mapper;

import com.kurepin.lab3.dto.TaskDto;
import com.kurepin.lab3.entities.Employee;
import com.kurepin.lab3.entities.Task;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@RequiredArgsConstructor
@Data
@Component
public class TaskMapper {
    private final ModelMapper modelMapper;

    public Task toEntity(TaskDto taskDto, Employee employee) throws ParseException {
        Task task = modelMapper.map(taskDto, Task.class);
        task.setEmployeeId(employee);
        return task;
    }
}
