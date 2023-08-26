package com.kurepin.lab3.dto;

import com.kurepin.lab3.enums.TaskType;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
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
