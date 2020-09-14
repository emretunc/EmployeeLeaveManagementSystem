package com.example.employeeleavemanagement.repository;

import com.example.employeeleavemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUsername(String username);

    @Query(value = "select * from Employees e where e.department_id = ?1 and e.role = 2", nativeQuery = true)
    Optional<Employee> findManagerByDepartmentId(Long id);
}
