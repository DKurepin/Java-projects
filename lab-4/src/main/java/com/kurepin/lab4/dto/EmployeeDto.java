package com.kurepin.lab4.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class EmployeeDto {
    private Long id;
    private String name;
    private OffsetDateTime date;
}
