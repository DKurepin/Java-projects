package com.kurepin.entities.mappers;

import com.kurepin.entities.dto.TaskDto;
import com.kurepin.entities.Employee;
import com.kurepin.entities.Task;
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

    public Task toEntity(TaskDto taskDto) throws ParseException {
        return modelMapper.map(taskDto, Task.class);
    }

    public TaskDto toDto(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }
}
