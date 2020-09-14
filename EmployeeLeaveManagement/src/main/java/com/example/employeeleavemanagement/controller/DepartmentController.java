package com.example.employeeleavemanagement.controller;

import com.example.employeeleavemanagement.model.Department;
import com.example.employeeleavemanagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/department/")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Admin create new department
     * @param department Department information
     * @return Return of the information of the newly created department
     */
    @PostMapping("createDepartment")
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.create(department);
    }

    /**
     * Admin update department
     * @param department Department information
     * @param id ID of the department to be updated
     * @return Return of the information of the updated department
     */
    @PutMapping("updateDepartment/{id}")
    public Department updateDepartment(@RequestBody Department department,
                                       @PathVariable String id) {
        return departmentService.update(department, Long.valueOf(id));
    }
}
