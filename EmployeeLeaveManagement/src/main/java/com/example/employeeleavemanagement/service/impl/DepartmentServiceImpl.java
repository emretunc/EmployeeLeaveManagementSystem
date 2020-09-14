package com.example.employeeleavemanagement.service.impl;

import com.example.employeeleavemanagement.model.Department;
import com.example.employeeleavemanagement.model.Employee;
import com.example.employeeleavemanagement.repository.DepartmentRepository;
import com.example.employeeleavemanagement.repository.EmployeeRepository;
import com.example.employeeleavemanagement.service.DepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Department create(Department department) {
        return departmentRepository.saveAndFlush(department);
    }

    @Override
    public Department update(Department department, Long id) {
        Department existDepartment = departmentRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(department, existDepartment, "id");
        return departmentRepository.saveAndFlush(existDepartment);
    }

    @Override
    @Transactional
    public void assignManager(Long employeeId, Long departmentId) {
        Employee newManager = employeeRepository.findById(employeeId).orElse(null);
        Department department = departmentRepository.findById(departmentId).orElse(null);

        // when change department manager, old manager role is employee now
        getManager(departmentId).ifPresent(oldManager -> oldManager.setRole(3));

        if (newManager != null) {
            Department oldDepartment = newManager.getDepartment();
            // when manager change department
            if( oldDepartment != null && !oldDepartment.getId().equals(departmentId) ) {
                oldDepartment.getEmployees().remove(newManager);
            }
            newManager.setDepartment(department);
            department.getEmployees().add(newManager);
            // promote employee too department manager
            if(newManager.getRole() == 3){
                newManager.setRole(2);
            }
        }


    }

    @Override
    @Transactional
    public void assignEmployee(Long employeeId, Long departmentId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        Department department = departmentRepository.findById(departmentId).orElse(null);

        if (department != null && employee != null) {
            department.getEmployees().add(employee);
            employee.setDepartment(department);
        }

    }


    @Override
    public Optional<Employee> getManager(Long id) {
        return employeeRepository.findManagerByDepartmentId(id);
    }
}
