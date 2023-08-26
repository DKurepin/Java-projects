package com.kurepin.lab4.controller;

import com.kurepin.lab4.dto.EmployeeDto;
import com.kurepin.lab4.entities.Employee;
import com.kurepin.lab4.mapper.EmployeeMapper;
import com.kurepin.lab4.security.services.AccessControlSecurity;
import com.kurepin.lab4.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;
    private final AccessControlSecurity accessControlSecurity;

    @GetMapping("/get_all_employees")
    @PreAuthorize("hasAuthority('read')")
    public List<EmployeeDto> getAllEmployees() {
        if (accessControlSecurity.isAdmin(username())) {
            return employeeService.getAllEmployees();
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_employee_by_id/{id}")
    @PreAuthorize("hasAuthority('read')")
    public EmployeeDto getEmployeeById(@PathVariable("id") Long id) {
        if (accessControlSecurity.employeeIsAllowed(username(), id)) {
            return employeeService.getEmployeeById(id);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @GetMapping("/get_all_employees_by_name/{name}")
    @PreAuthorize("hasAuthority('read')")
    public List<EmployeeDto> getAllEmployeesByName(@PathVariable("name") String name) {
        if (accessControlSecurity.isAdmin(username())) {
            return employeeService.getAllEmployeesByName(name);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @PostMapping("/add_employee")
    @PreAuthorize("hasAuthority('update')")
    public Employee addEmployee(@RequestBody EmployeeDto employeeDTO) throws ParseException {
        if (accessControlSecurity.isAdmin(username())) {
            Employee employee = employeeMapper.toEntity(employeeDTO);
            return employeeService.saveEmployee(employee);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_employee_by_id/{id}")
    @PreAuthorize("hasAuthority('update')")
    public void deleteEmployeeById(@PathVariable Long id) {
        if (accessControlSecurity.isAdmin(username())) {
            employeeService.deleteByEmployeeId(id);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_employee")
    @PreAuthorize("hasAuthority('update')")
    public void deleteEmployee(@RequestBody EmployeeDto employeeDTO) throws ParseException {
        if (accessControlSecurity.isAdmin(username())) {
            Employee employee = employeeMapper.toEntity(employeeDTO);
            employeeService.deleteByEmployee(employee);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete_all_employees")
    @PreAuthorize("hasAuthority('update')")
    public void deleteAllEmployees() {
        if (accessControlSecurity.isAdmin(username())) {
            employeeService.deleteAllEmployees();
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    @PutMapping("/update_employee/{id}")
    @PreAuthorize("hasAuthority('update')")
    public Employee updateEmployee(@RequestBody EmployeeDto employeeDTO, @PathVariable("id") Long id) throws ParseException {
        if (accessControlSecurity.employeeIsAllowed(username(), id)) {
            EmployeeDto employeeDTODB = employeeService.getEmployeeById(id);
            employeeDTO.setId(employeeDTODB.getId());
            Employee employee = employeeMapper.toEntity(employeeDTO);
            return employeeService.updateEmployee(employee);
        }
        throw new AccessDeniedException("403 Forbidden");
    }

    private String username() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
