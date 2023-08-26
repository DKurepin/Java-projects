package com.kurepin.service;

import com.kurepin.entities.Employee;
import com.kurepin.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getAllByName(String name);
    List<Task> getAllTasksByEmployeeId(Employee employee);
}
