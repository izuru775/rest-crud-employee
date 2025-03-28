package com.isuru.springboot.cruddemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isuru.springboot.cruddemo.entity.Employee;
import com.isuru.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private ObjectMapper objectMapper;
    private EmployeeService employeeService;
    // Quick and dirty: inject employee dao (use constructor injection)
    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService,ObjectMapper theObjectMapper) {
        employeeService = theEmployeeService;
        objectMapper = theObjectMapper;
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

    // Add mapping for PATCH /employees/{employeeId} - patch employee ... partial update
    @PatchMapping("/employees/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId,
                                  @RequestBody Map<String,Object> patchPayload){
        // Retrieve the employee from the database
        Employee tempEmployee = employeeService.findById(employeeId);

        // Throw exception if null
        if(tempEmployee == null){
            throw new RuntimeException("Employee id not found - " + employeeId);
        }
        // Throw exception if the request body contains "id" key
        if(patchPayload.containsKey("id")){
            throw new RuntimeException("Employee id not allowed in request body - " + employeeId);
        }
        // Apply patch payload to the employee
        Employee patchedEmployee = apply(patchPayload,tempEmployee);

        Employee dbEmployee = employeeService.save(patchedEmployee);

        return dbEmployee;
    }

    private Employee apply(Map<String, Object> patchPayload, Employee tempEmployee) {
        // Convert employee object to a JSON object node
        ObjectNode employeeNode = objectMapper.convertValue(tempEmployee,ObjectNode.class);
        // Convert patchPayload map to a JSON object node
        ObjectNode patchNode = objectMapper.convertValue(patchPayload,ObjectNode.class);
        //  Merge the patch updates into the employee node
        employeeNode.setAll(patchNode);

        return objectMapper.convertValue(employeeNode,Employee.class);
    }













































}
