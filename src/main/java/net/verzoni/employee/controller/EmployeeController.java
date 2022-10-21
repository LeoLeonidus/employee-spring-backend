package net.verzoni.employee.controller;

import net.verzoni.employee.exception.ResourceNotFoundException;
import net.verzoni.employee.model.Employee;
import net.verzoni.employee.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/")
public class EmployeeController {

    @Autowired
    private EmployeeRepo employeeRepo;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeRepo.findAll();
    }

    // Add new Employee rest api
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepo.save(employee);
    }

    // get employee by id rest api
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee>  getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Employee not found with id :"+id));
        return ResponseEntity.ok(employee) ;
    }

    // update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id , @RequestBody Employee employeeUpdate) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Employee not found with id :"+id));

        employee.setFirstName(employeeUpdate.getFirstName());
        employee.setLastName(employeeUpdate.getLastName());
        employee.setEmail(employeeUpdate.getEmail());

        Employee updatedEmployee = employeeRepo.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // delete employee rest api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Employee not found with id :"+id));
        employeeRepo.deleteById(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",true);
        return ResponseEntity.ok(response);
    }
}
