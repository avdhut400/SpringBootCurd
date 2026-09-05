package com.example.employee.repository;

import com.example.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<List<Employee>>  findByDepartment(String department);

    boolean findByName(String name);

    boolean existsByName(String name);
}
