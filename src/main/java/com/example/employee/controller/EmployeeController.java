package com.example.employee.controller;

import com.example.employee.entity.Employee;
import com.example.employee.model.EmployeeAddRequest;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import pcodes.jpaproject.ems.model.EmployeeAddResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping
    public ResponseEntity<EmployeeAddResponse> addEmployee(
            @RequestBody EmployeeAddRequest request) {

        return ResponseEntity.ok(employeeService.addEmployee(request));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeAddResponse>> getAllEmployees() {

        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
//    public ResponseEntity<EmployeeAddResponse> getEmployee(
//            @PathVariable Long id) {
//
//        EmployeeAddResponse employee = employeeService.getEmployee(id);
//
//        if (employee == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(employee);
//    }
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {

        try {
            EmployeeAddResponse employee = employeeService.getEmployee(id);

            return ResponseEntity.ok(employee);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(404)
                    .body("Employee not found with id: " + id);
        }
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<Optional<List<Employee>>>
    getAllEmployeesOfDepartment(@PathVariable String department) {

        return ResponseEntity.ok(
                employeeService.getAllEmployeesOfDepartment(department)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @RequestBody EmployeeAddRequest request,
            @PathVariable Long id) {

        Employee employee = employeeService.updateEmployee(request, id);

        if (employee == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeEmployee(@PathVariable Long id) {

        employeeService.removeEmployee(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeAllEmployees() {

        employeeService.removeAllEmployees();

        return ResponseEntity.noContent().build();
    }
}

