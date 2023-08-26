package com.kurepin.main;

import connection.JdbcUtil;
import connection.MyBatisUtil;
import enums.TaskType;
import hibernate.daoObjects.HibernateEmployeeDAO;
import hibernate.entities.HibernateEmployee;
import jdbc.daoObjects.JdbcEmployeeDAO;
import jdbc.daoObjects.JdbcTaskDAO;
import jdbc.entities.JdbcEmployee;
import myBatis.daoObjects.MyBatisEmployeeDAO;
import myBatis.daoObjects.MyBatisTaskDAO;
import myBatis.entities.MyBatisEmployee;
import myBatis.entities.MyBatisTask;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

// JDBC
//        Employee newEmployee = new Employee();
//        newEmployee.setId(1);
//        newEmployee.setName("Ahmed");
//        newEmployee.setDate(Date.valueOf("2003-10-10"));
//        EmployeeDAO employeeDAO = new EmployeeDAO();
//        employeeDAO.save(newEmployee);
//        Employee newEmployee2 = new Employee();
//        newEmployee2.setId(2);
//        newEmployee2.setName("Bebrik");
//        newEmployee2.setDate(Date.valueOf("2020-10-10"));
//        EmployeeDAO employeeDAO2 = new EmployeeDAO();
//        employeeDAO2.save(newEmployee2);
//        Employee newEmployee3 = new Employee();
//        newEmployee3.setId(3);
//        newEmployee3.setName("Cemal");
//        newEmployee3.setDate(Date.valueOf("2010-10-10"));
//        EmployeeDAO employeeDAO3 = new EmployeeDAO();
//        employeeDAO3.save(newEmployee3);

//        Task newTask = new Task();
//        newTask.setId(1);
//        newTask.setName("Task1");
//        newTask.setDeadline(Date.valueOf("2024-10-10"));
//        newTask.setDescription("Description1");
//        newTask.setType(TaskType.BUG);
//        newTask.setEmployeeId(1);
//        TaskDAO taskDAO = new TaskDAO();
//        taskDAO.save(newTask);
//        Task newTask2 = new Task();
//        newTask2.setId(2);
//        newTask2.setName("Task2");
//        newTask2.setDeadline(Date.valueOf("2025-09-09"));
//        newTask2.setDescription("Description2");
//        newTask2.setType(TaskType.FEATURE);
//        newTask2.setEmployeeId(1);
//        TaskDAO taskDAO2 = new TaskDAO();
//        taskDAO2.save(newTask2);
//        Task newTask3 = new Task();
//        newTask3.setId(3);
//        newTask3.setName("Task3");
//        newTask3.setDeadline(Date.valueOf("2026-08-08"));
//        newTask3.setDescription("Description3");
//        newTask3.setType(TaskType.IMPROVEMENT);
//        newTask3.setEmployeeId(3);
//        TaskDAO taskDAO3 = new TaskDAO();
//        taskDAO3.save(newTask3);
//
//
//        Task newTask3 = new Task();
//        newTask3.setId(3);
//        newTask3.setName("Task3");
//        newTask3.setDeadline(Date.valueOf("2026-08-08"));
//        newTask3.setDescription("Description3");
//        newTask3.setType(TaskType.BUG);
//        newTask3.setEmployeeId(3);
//        JdbcTaskDAO taskDAO3 = new JdbcTaskDAO();
//        Task result = taskDAO3.update(newTask3);

// HIBERNATE
//        Employee employee = new Employee();
//        employee.setId(21);
//        employee.setName("Alexander");
//        employee.setDate(Date.valueOf("2023-10-10"));
//
//
//        Task task = new Task();
//        task.setId(2);
//        task.setName("Task2");
//        task.setDeadline(Date.valueOf("2026-08-08"));
//        task.setDescription("Description2");
//        task.setType(TaskType.FEATURE);
//        task.setEmployee(employee);
//        HibernateTaskDAO taskDAO = new HibernateTaskDAO();
//        taskDAO.save(task);
//
//        HibernateEmployeeDAO employeeDAO = new HibernateEmployeeDAO();
//        List<HibernateEmployee> employees = employeeDAO.getAll();

// MYBATIS
//        MyBatisTaskDAO taskDAO = new MyBatisTaskDAO();
//        List<MyBatisTask> tasks = taskDAO.getAllByEmployeeId(1);
//        System.out.println(tasks);
//
//        MyBatisEmployeeDAO employeeDAO = new MyBatisEmployeeDAO();
//        List<MyBatisEmployee> employees = employeeDAO.getAll();
//        for (MyBatisEmployee employee : employees) {
//            System.out.println(employee);
//        }

//        MyBatisEmployee employee = new MyBatisEmployee();
//        employee.setId(1000);
//        employee.setName("Afonya");
//        employee.setDate(Date.valueOf("2000-10-11"));

        final double NANOSECONDS_IN_SECOND = 1_000_000_000;

        JdbcEmployee employee = new JdbcEmployee();
        double jdbcStartInsert = System.nanoTime();
        for (int i = 1; i < 101; i++) {
            employee.setId(i);
            employee.setName("Afonya");
            employee.setDate(Date.valueOf("2000-10-11"));
            JdbcEmployeeDAO employeeDAO = new JdbcEmployeeDAO();
            employeeDAO.save(employee);
        }
        double jdbcEndInsert = System.nanoTime();
        double jdbcInsert = (jdbcEndInsert - jdbcStartInsert) / NANOSECONDS_IN_SECOND;

        double jdbcStartSelect = System.nanoTime();
        JdbcEmployeeDAO employeeDAO1 = new JdbcEmployeeDAO();
        List<JdbcEmployee> employees = employeeDAO1.getAll();
        double jdbcEndSelect = System.nanoTime();
        double jdbcSelect = (jdbcEndSelect - jdbcStartSelect) / NANOSECONDS_IN_SECOND;


        JdbcEmployeeDAO employeeDAO2 = new JdbcEmployeeDAO();
        employeeDAO2.deleteAll();

        HibernateEmployee employee1 = new HibernateEmployee();
        double hibernateStartInsert = System.nanoTime();
        for (int i = 1; i < 101; i++) {
            employee1.setId(i);
            employee1.setName("Afonya");
            employee1.setDate(Date.valueOf("2000-10-11"));
            HibernateEmployeeDAO employeeDAO = new HibernateEmployeeDAO();
            employeeDAO.save(employee1);
        }
        double hibernateEndInsert = System.nanoTime();
        double hibernateInsert = (hibernateEndInsert - hibernateStartInsert) / NANOSECONDS_IN_SECOND;

        double hibernateStartSelect = System.nanoTime();
        HibernateEmployeeDAO employeeDAO3 = new HibernateEmployeeDAO();
        List<HibernateEmployee> employees1 = employeeDAO3.getAll();
        double hibernateEndSelect = System.nanoTime();
        double hibernateSelect = (hibernateEndSelect - hibernateStartSelect) / NANOSECONDS_IN_SECOND;

        HibernateEmployeeDAO employeeDAO4 = new HibernateEmployeeDAO();
        employeeDAO4.deleteAll();

        MyBatisEmployee employee2 = new MyBatisEmployee();
        double myBatisStartInsert = System.nanoTime();
        for (int i = 1; i < 101; i++) {
            employee2.setId(i);
            employee2.setName("Afonya");
            employee2.setDate(Date.valueOf("2000-10-11"));
            MyBatisEmployeeDAO employeeDAO = new MyBatisEmployeeDAO();
            employeeDAO.save(employee2);
        }
        double myBatisEndInsert = System.nanoTime();
        double myBatisInsert = (myBatisEndInsert - myBatisStartInsert) / NANOSECONDS_IN_SECOND;

        double myBatisStartSelect = System.nanoTime();
        MyBatisEmployeeDAO employeeDAO5 = new MyBatisEmployeeDAO();
        List<MyBatisEmployee> employees2 = employeeDAO5.getAll();
        double myBatisEndSelect = System.nanoTime();
        double myBatisSelect = (myBatisEndSelect - myBatisStartSelect) / NANOSECONDS_IN_SECOND;

        MyBatisEmployeeDAO employeeDAO6 = new MyBatisEmployeeDAO();
        employeeDAO6.deleteAll();


        System.out.println("JDBC: " + "Insert: " + jdbcInsert);
        System.out.println("JDBC: " + "Select: " + jdbcSelect);

        System.out.println("Hibernate: " + "Insert: " + hibernateInsert);
        System.out.println("Hibernate: " + "Select: " + hibernateSelect);
        
        System.out.println("MyBatis: " + "Insert: " + myBatisInsert);
        System.out.println("MyBatis: " + "Select: " + myBatisSelect);
    }
}


