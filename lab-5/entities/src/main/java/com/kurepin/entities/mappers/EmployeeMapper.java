package com.kurepin.entities.mappers;

import com.kurepin.entities.dto.EmployeeDto;
import com.kurepin.entities.Employee;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Data
@Component
public class EmployeeMapper {


    private final ModelMapper modelMapper;

    public Employee toEntity(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }

    public EmployeeDto toDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }
}
