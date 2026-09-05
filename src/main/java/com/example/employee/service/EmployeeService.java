package com.example.employee.service;

import com.example.employee.entity.Employee;
import com.example.employee.model.EmployeeAddRequest;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import pcodes.jpaproject.ems.model.EmployeeAddResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }
    public EmployeeAddResponse addEmployee(EmployeeAddRequest request){
        Employee employee=new Employee();
        if(!employeeRepository.existsByName(request.getName())){
            employee.setName(request.getName());
            employee.setDepartment(request.getDepartment());
            employee.setSalary(request.getSalary());
            Employee storedEmployee=employeeRepository.save(employee);
            return new EmployeeAddResponse(storedEmployee.getId(),storedEmployee.getName());
        }
        return new EmployeeAddResponse(null,null);

    }
    public  List<EmployeeAddResponse> getAllEmployees(){
        List<Employee> employee=employeeRepository.findAll();
        List<EmployeeAddResponse>employees = new ArrayList<>();
        for(Employee e:employee){
            EmployeeAddResponse n=new EmployeeAddResponse();
            n.setId(e.getId());
            n.setName(e.getName());
            employees.add(n);
        }
        return employees;
    }
    public EmployeeAddResponse getEmployee(Long id) {
        if (id == null) return null;
        Employee employee=employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("user Not found"));
        return new EmployeeAddResponse(employee.getId(),employee.getName());

    }
    public Optional<List<Employee>> getAllEmployeesOfDepartment(String department){
        return employeeRepository.findByDepartment(department);
    }
    public Employee updateEmployee(EmployeeAddRequest request, Long id) {
        if (id == null || request == null) return null;

        Employee dbEmployee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Employee found"));

        if (request.getName() != null) dbEmployee.setName(request.getName());
        if (request.getDepartment() != null) dbEmployee.setDepartment(request.getDepartment());
        if (request.getSalary() != null) dbEmployee.setSalary(request.getSalary());

        return employeeRepository.save(dbEmployee);
    }
    public void removeEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public void removeAllEmployees() {
        employeeRepository.deleteAll();
    }
}
