package com.kurepin.lab3.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.OffsetDateTime;

@Data
@Builder
public class EmployeeDto {
    private Long id;
    private String name;
    private OffsetDateTime date;
}
