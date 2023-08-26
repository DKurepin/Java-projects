package com.kurepin.service;


import com.kurepin.entities.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    void deleteByEmployeeId(Long id);
    void deleteByEmployee(EmployeeDto employeeDto);
    void deleteAllEmployees();
    EmployeeDto updateEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeById(Long id);
    List<EmployeeDto> getAllEmployees();
    List<EmployeeDto> getAllEmployeesByName(String name);
}
