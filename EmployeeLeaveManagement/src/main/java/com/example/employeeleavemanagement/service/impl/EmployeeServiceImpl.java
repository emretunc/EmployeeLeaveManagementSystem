package com.example.employeeleavemanagement.service.impl;

import com.example.employeeleavemanagement.model.Employee;
import com.example.employeeleavemanagement.model.LeaveDraft;
import com.example.employeeleavemanagement.repository.EmployeeRepository;
import com.example.employeeleavemanagement.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<Employee> findByID(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee create(Employee employee) {
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        return employeeRepository.saveAndFlush(employee);
    }

/**
 * Employee username, password, department, drafts
 * and role are kept constant. Other information can be changed.
 */
    @Override
    public Employee update(Employee employee, Long id) {
        Employee existEmployee = employeeRepository.findById(id).orElse(null);
        assert existEmployee != null;
        employee.setRole(existEmployee.getRole());
        employee.setDepartment(existEmployee.getDepartment());
        employee.setDrafts(existEmployee.getDrafts());
        employee.setPassword(existEmployee.getPassword());
        employee.setUsername(existEmployee.getUsername());
        BeanUtils.copyProperties(employee, existEmployee, "id");
        return employeeRepository.saveAndFlush(existEmployee);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }








}
