package com.example.employeeleavemanagement.repository;

import com.example.employeeleavemanagement.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
