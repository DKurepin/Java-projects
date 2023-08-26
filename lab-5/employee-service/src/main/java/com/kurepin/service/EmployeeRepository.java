package com.kurepin.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kurepin.entities.Employee;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> getAllByName(String name);
}
