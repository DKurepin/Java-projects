package com.kurepin.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kurepin.entities.enums.TaskType;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Data
@Builder
@JsonSerialize
@JsonDeserialize
@RequiredArgsConstructor
public class TaskDto {
    private Long id;
    private String name;
    private Date deadline;
    private String description;
    private TaskType type;
    private String author;
    private Long employeeId;

    @JsonCreator
    public TaskDto(@JsonProperty("id") Long id, @JsonProperty("name") String name,
                   @JsonProperty("deadline") Date deadline,
                   @JsonProperty("description") String description,
                   @JsonProperty("type") TaskType type,
                   @JsonProperty("author") String author,
                   @JsonProperty("employeeId") Long employeeId) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.description = description;
        this.type = type;
        this.author = author;
        this.employeeId = employeeId;
    }
}
