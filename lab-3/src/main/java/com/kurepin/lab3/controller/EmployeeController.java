package com.kurepin.lab3.controller;

import com.kurepin.lab3.dto.EmployeeDto;
import com.kurepin.lab3.entities.Employee;
import com.kurepin.lab3.mapper.EmployeeMapper;
import com.kurepin.lab3.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;

    @GetMapping("/get_all_employees")
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/get_employee_by_id/{id}")
    public EmployeeDto getEmployeeById(@PathVariable("id") Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/get_all_employees_by_name/{name}")
    public List<EmployeeDto> getAllEmployeesByName(@PathVariable("name") String name) {
        return employeeService.getAllEmployeesByName(name);
    }

    @PostMapping("/add_employee")
    public Employee addEmployee(@RequestBody EmployeeDto employeeDTO) throws ParseException {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        return employeeService.saveEmployee(employee);
    }

    @DeleteMapping("/delete_employee_by_id/{id}")
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteByEmployeeId(id);
    }

    @DeleteMapping("/delete_employee")
    public void deleteEmployee(@RequestBody EmployeeDto employeeDTO) throws ParseException {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employeeService.deleteByEmployee(employee);
    }

    @DeleteMapping("/delete_all_employees")
    public void deleteAllEmployees() {
        employeeService.deleteAllEmployees();
    }

    @PutMapping("/update_employee/{id}")
    public Employee updateEmployee(@RequestBody EmployeeDto employeeDTO, @PathVariable("id") Long id) throws ParseException {
        EmployeeDto employeeDTODB = employeeService.getEmployeeById(id);
        employeeDTO.setId(employeeDTODB.getId());
        Employee employee = employeeMapper.toEntity(employeeDTO);
        return employeeService.updateEmployee(employee);
    }
}
