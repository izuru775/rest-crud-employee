package com.isuru.springboot.cruddemo.rest;

import com.isuru.springboot.cruddemo.entity.Employee;
import com.isuru.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private EmployeeService employeeService;
    // Quick and dirty: inject employee dao (use constructor injection)
    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    // Expose "/employees" and return a list of employees
    @GetMapping("/employees")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }
    // Add mapping to GET /employee/{employeeId} -find an employee by id
    @GetMapping("/employees/{employeeId}")
    public Employee findById(@PathVariable int employeeId){
        Employee theEmployee = employeeService.findById(employeeId);
        if(theEmployee == null){
            throw new RuntimeException("Employee id not found - "+ employeeId);
        }
        return theEmployee;
    }
    // Add mapping for POST /employees - add new employee
    @PostMapping("/employees")
    public Employee save(@RequestBody Employee myEmployee){
        myEmployee.setId(0);
        return employeeService.save(myEmployee);
    }
    // Add mapping for PUT /employees - update an existing employee
    @PutMapping("/employees")
    public Employee update(@RequestBody Employee myEmployee){
        return employeeService.save(myEmployee);
    }
    // Add mapping for DELETE /employees - delete an employee using id
    @DeleteMapping("/employees/{employeeId}")
    public void deleteById(@PathVariable int employeeId){
        employeeService.deleteById(employeeId);
    }
}
