package com.isuru.springboot.cruddemo.dao;

import com.isuru.springboot.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO{
    // Define fields for entityManager
    private EntityManager entityManager;
    // Setup Constructor injection
    @Autowired
    public EmployeeDAOJpaImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public List<Employee> findAll() {
        // Create a query
        TypedQuery<Employee> theQuery = entityManager.createQuery("SELECT e FROM Employee e",Employee.class);
        // Execute query and get result list
        List<Employee> employees = theQuery.getResultList();
        // Return the results
        return employees;
    }

    @Override
    public Employee findById(int theId) {
        // Get employee
        Employee theEmployee = entityManager.find(Employee.class,theId);
        // Return employee
        return theEmployee;
    }

    @Override
    public Employee save(Employee anEmployee) {
        // Save Employee
        Employee dbEmployee = entityManager.merge(anEmployee);
        // Return the dbEmployee
        return dbEmployee;
    }

    @Override
    public void deleteById(int Id) {
        // Find employee by id\
        Employee theEmployee = entityManager.find(Employee.class,Id);
        // Remove employee
        entityManager.remove(theEmployee);
    }

}
