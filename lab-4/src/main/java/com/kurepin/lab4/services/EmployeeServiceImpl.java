package com.kurepin.lab4.services;

import com.kurepin.lab4.dto.EmployeeDto;
import com.kurepin.lab4.entities.Employee;
import com.kurepin.lab4.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteByEmployeeId(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void deleteByEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.getReferenceById(id);
        EmployeeDto employeeDTO = EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .date(employee.getDate())
                .build();
        return employeeDTO;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = employees.stream().map(employee -> EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .date(employee.getDate())
                .build()).toList();
        return employeeDtos;
    }

    @Override
    public List<EmployeeDto> getAllEmployeesByName(String name) {
        List<Employee> employees = employeeRepository.getAllByName(name);
        List<EmployeeDto> employeeDtos = employees.stream().map(employee -> EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .date(employee.getDate())
                .build()).toList();
        return employeeDtos;
    }
}
