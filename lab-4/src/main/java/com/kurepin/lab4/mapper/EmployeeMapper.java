package com.kurepin.lab4.mapper;

import com.kurepin.lab4.dto.EmployeeDto;
import com.kurepin.lab4.entities.Employee;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@RequiredArgsConstructor
@Data
@Component
public class EmployeeMapper {
    private final ModelMapper modelMapper;

    public Employee toEntity(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }
}
