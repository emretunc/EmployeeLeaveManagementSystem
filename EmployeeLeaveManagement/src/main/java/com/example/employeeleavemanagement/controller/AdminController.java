package com.example.employeeleavemanagement.controller;

import com.example.employeeleavemanagement.model.Department;
import com.example.employeeleavemanagement.model.Employee;
import com.example.employeeleavemanagement.service.DepartmentService;
import com.example.employeeleavemanagement.service.EmployeeService;
import com.example.employeeleavemanagement.utilities.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @Autowired
    public AdminController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    /**
     * @return Returns a list of employees belonging to all roles
     */
    @GetMapping("/listUser")
    public List<Employee> listEmployee(){
        return employeeService.getAll();
    }

    /**
     * @param id employee id
     * @return Relevant employee information is returned
     */
    @GetMapping("/show/{id}")
    public String getEmployee(@PathVariable String id) {
        return employeeService.findByID(Long.valueOf(id)).get().getEmployee_name();
    }

    /**
     * Admin update relevant employee information
     * @param id employee id
     * @param employee updated employee information
     * @return changed user information is returned
     */
    @PutMapping("/updateEmployee/{id}")
    public Employee updateEmployee(@RequestBody Employee employee,
                                   @PathVariable String id) {

        employee.setRole(Roles.ROLE_EMPLOYEE.getValue());
        return employeeService.update(employee, Long.valueOf(id));
    }

    /**
     * Admin create new employee
     * @param employee new employee information
     * @return newly created employee is returned
     */
    @PostMapping("/createEmployee")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    /**
     * Admin cdelete employee
     * @param id id of the employee to be deleted
     */
    @DeleteMapping("/deleteEmployee/{id}")
    public  void deleteEmployee(@PathVariable String id){
        employeeService.delete(Long.valueOf(id));
    }

    /**
     *
     * The process of assigning employees to the department where they will work
     * @param employeeId ID of the employee to be appointed
     * @param departmentId  The id of the department to be assigned a employee
     */
    @PutMapping("/assignEmployee/{employeeId}-{departmentId}")
    public void assignEmployee(@PathVariable String employeeId,
                               @PathVariable String departmentId){
        departmentService.assignEmployee(Long.valueOf(employeeId), Long.valueOf(departmentId));
    }

    /**
     *
     * @param employeeId ID of the employee to be appointed as manager
     * @param departmentId  The id of the department to be assigned a manager
     */
    @PutMapping("/assignManager/{employeeId}-{departmentId}")
    public void assignManager(@PathVariable String employeeId,
                              @PathVariable String departmentId) {

        departmentService.assignManager(Long.valueOf(employeeId), Long.valueOf(departmentId));
    }




}
