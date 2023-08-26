package com.kurepin.lab3.repositories;

import com.kurepin.lab3.entities.Employee;
import com.kurepin.lab3.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getAllByName(String name);
    List<Task> getAllTasksByEmployeeId(Employee employeeId);
}
