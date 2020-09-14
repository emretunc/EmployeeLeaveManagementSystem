package com.example.employeeleavemanagement.service;

import com.example.employeeleavemanagement.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public Optional<Employee> findByID(Long id);
    public List<Employee> getAll();
    public Employee create(Employee employee);
    public Employee update(Employee employee, Long id);
    public void delete(Long id);
}
