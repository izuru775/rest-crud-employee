package com.isuru.springboot.cruddemo.service;

import com.isuru.springboot.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
}
