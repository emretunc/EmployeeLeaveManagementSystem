package com.example.employeeleavemanagement.controller;

import com.example.employeeleavemanagement.model.Employee;
import com.example.employeeleavemanagement.repository.EmployeeRepository;
import com.example.employeeleavemanagement.service.LeaveDraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ManagerController {

    private final EmployeeRepository employeeRepository;
    private final LeaveDraftService leaveDraftService;

    @Autowired
    public ManagerController(EmployeeRepository employeeRepository, LeaveDraftService leaveDraftService) {
        this.employeeRepository = employeeRepository;
        this.leaveDraftService = leaveDraftService;
    }


    /**
     * The drafts of the employees in the manager department
     * are approved if the conditions are met.
     * @param id ID of the draft to be approved
     * @param authentication
     */
    @PutMapping("manager/confirmDraft/{id}")
    public void confirmDraft(@PathVariable String id, Authentication authentication){
        Employee existManager = employeeRepository.findByUsername(authentication.getName()).get();
        leaveDraftService.confirmDraft(existManager.getId(), Long.valueOf(id));
    }


}
