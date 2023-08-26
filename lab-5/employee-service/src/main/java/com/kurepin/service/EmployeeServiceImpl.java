package com.kurepin.service;


import com.kurepin.entities.mappers.EmployeeMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.kurepin.entities.Employee;
import com.kurepin.entities.dto.EmployeeDto;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@ComponentScan(basePackages = "com.kurepin.entities.mappers")
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    @Autowired
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Override
    public void deleteByEmployeeId(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void deleteByEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        employeeRepository.delete(employee);
    }

    @Override
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        EmployeeDto employeeDTO = employeeMapper.toDto(employee.get());
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
