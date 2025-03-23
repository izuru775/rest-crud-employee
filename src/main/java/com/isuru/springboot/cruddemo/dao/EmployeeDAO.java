package com.isuru.springboot.cruddemo.dao;

import com.isuru.springboot.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> findAll();
}
