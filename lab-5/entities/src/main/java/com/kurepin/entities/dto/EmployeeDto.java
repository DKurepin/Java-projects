package com.kurepin.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Builder
@Data
@JsonSerialize
@JsonDeserialize
@RequiredArgsConstructor
public class EmployeeDto {
    private Long id;
    private String name;
    private Date date;

    @JsonCreator
    public EmployeeDto(@JsonProperty("id") Long id, @JsonProperty("name") String name, @JsonProperty("date") Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
}
