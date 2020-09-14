package com.example.employeeleavemanagement.service;

import com.example.employeeleavemanagement.model.Department;
import com.example.employeeleavemanagement.model.Employee;

import java.util.Optional;

public interface DepartmentService {

    public Department create(Department department);
    public Department update(Department department, Long id);
    public void assignManager(Long employeeId, Long departmentId);
    public void assignEmployee(Long employeeId, Long departmentId);
    public Optional<Employee> getManager(Long id);
}
