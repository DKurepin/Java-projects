package com.kurepin.lab4.services;

import com.kurepin.lab4.dto.EmployeeDto;
import com.kurepin.lab4.entities.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    void deleteByEmployeeId(Long id);
    void deleteByEmployee(Employee employee);
    void deleteAllEmployees();
    Employee updateEmployee(Employee employee);
    EmployeeDto getEmployeeById(Long id);
    List<EmployeeDto> getAllEmployees();
    List<EmployeeDto> getAllEmployeesByName(String name);
}
