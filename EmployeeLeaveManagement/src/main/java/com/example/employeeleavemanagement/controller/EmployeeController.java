package com.example.employeeleavemanagement.controller;

import com.example.employeeleavemanagement.model.Employee;
import com.example.employeeleavemanagement.model.LeaveDraft;
import com.example.employeeleavemanagement.repository.EmployeeRepository;
import com.example.employeeleavemanagement.service.LeaveDraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final LeaveDraftService leaveDraftService;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, LeaveDraftService leaveDraftService) {
        this.employeeRepository = employeeRepository;
        this.leaveDraftService = leaveDraftService;
    }

    /**
     * Returns the number of leaves available to the authenticated employee
     * @param authentication
     * @return Returns the number of available leaves of the employee
     */
    @GetMapping("/control")
    public Integer getLeaveNumber(Authentication authentication){
        Employee existEmployee = employeeRepository.findByUsername(authentication.getName()).orElse(null);
        return existEmployee.getLeaves_number();
    }


    /**
     * A draft pending approval is created for the authenticated employee,
     * whose number of leave is determined as 5 by default.
     * @param authentication
     * @return the created draft is returned
     */
    @PostMapping("/createDraft")
    public LeaveDraft createDraft(Authentication authentication) {
        Employee existEmployee = employeeRepository.findByUsername(authentication.getName()).orElse(null);
        return leaveDraftService.create(existEmployee.getId());
    }

    /**
     * If the manager does not sanction the draft,
     * the employee deletes the draft.
     * @param authentication
     * @param id draft id to be deleted
     */
    @DeleteMapping("/deleteDraft/{id}")
    public void deleteDraft(Authentication authentication,@PathVariable String id) {
        Employee existEmployee = employeeRepository.findByUsername(authentication.getName()).get();
        leaveDraftService.delete(existEmployee.getId(), Long.valueOf(id));
    }

    /**
     * If the manager does not sanction the draft,
     * the employee update the draft.
     * @param authentication
     * @param id draft id to be deleted
     * @param leaveDraft updated draft information
     */
    @PutMapping("/updateDraft/{id}")
    public LeaveDraft updateDraft(Authentication authentication,
                                  @PathVariable String id,
                                  @RequestBody LeaveDraft leaveDraft) {
        Employee existEmployee = employeeRepository.findByUsername(authentication.getName()).get();
        return leaveDraftService.update(existEmployee.getId(), Long.valueOf(id), leaveDraft);
    }

    /**
     * The authenticated user checks the approved permission drafts.
     * @param authentication
     * @return Employees' approved drafts are returned
     */

    @GetMapping("/getDrafts")
    public List<LeaveDraft> getConfirmedDraft(Authentication authentication){
        Employee existEmployee = employeeRepository.findByUsername(authentication.getName()).get();
        return leaveDraftService.getConfirmedDraft(existEmployee.getId());
    }
}
