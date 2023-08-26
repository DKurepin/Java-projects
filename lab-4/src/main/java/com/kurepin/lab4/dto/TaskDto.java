package com.kurepin.lab4.dto;

import com.kurepin.lab4.enums.TaskType;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class TaskDto {
    private Long id;
    private String name;
    private OffsetDateTime deadline;
    private String description;
    private TaskType type;
    private String author;
    private Long employeeId;
}
