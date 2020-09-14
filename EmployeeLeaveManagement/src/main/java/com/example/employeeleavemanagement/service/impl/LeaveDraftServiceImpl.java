package com.example.employeeleavemanagement.service.impl;

import com.example.employeeleavemanagement.model.Employee;
import com.example.employeeleavemanagement.model.LeaveDraft;
import com.example.employeeleavemanagement.repository.LeaveDraftRepository;
import com.example.employeeleavemanagement.service.EmployeeService;
import com.example.employeeleavemanagement.service.LeaveDraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveDraftServiceImpl implements LeaveDraftService {

    private final EmployeeService employeeService;
    private final LeaveDraftRepository leaveDraftRepository;

    @Autowired
    public LeaveDraftServiceImpl(EmployeeService employeeService, LeaveDraftRepository leaveDraftRepository) {
        this.employeeService = employeeService;
        this.leaveDraftRepository = leaveDraftRepository;
    }

    @Override
    @Transactional
    public LeaveDraft create(Long userId) {
        Employee authenticatedEmployee = employeeService.findByID(userId).orElse(null);
        if(authenticatedEmployee == null) {
            return null;
        }
        LeaveDraft leaveDraft = new LeaveDraft();
        leaveDraft.setEmployee(authenticatedEmployee);
        leaveDraftRepository.save(leaveDraft);

        authenticatedEmployee.getDrafts().add(leaveDraft);
        return leaveDraft;
    }

    @Override
    @Transactional
    public LeaveDraft update(Long userId, Long draftId, LeaveDraft leaveDraft) {
        LeaveDraft existLeaveDraft = leaveDraftRepository.findById(draftId).get();

        if(!existLeaveDraft.getEmployee().getId().equals(userId)) {
            System.out.println("Employee not matched with draft");
            return null;
        }

        if(existLeaveDraft.getEmployee().getId().equals(userId) && existLeaveDraft.getIsValid() == null)  {
            existLeaveDraft.setLeave_days(leaveDraft.getLeave_days());
            System.out.println("Draft updated");
        } else {
            System.out.println("Draft accepted or rejected, draft cannot be changed");
        }

        return existLeaveDraft;
    }

    @Override
    public String delete(Long userId, Long draftId) {
        LeaveDraft existLeaveDraft = leaveDraftRepository.findById(draftId).get();
//        Draft confirmation must be false for deletion
//        User and draft must be match
//        Manager should not reject or accept the draft
        if(existLeaveDraft.getEmployee().getId().equals(userId) && existLeaveDraft.getIsValid() == null) {
            leaveDraftRepository.deleteById(draftId);
            return "Draft deletion success";
        } else {
            return "Draft accepted or rejected, draft cannot be changed";
        }
    }

    @Override
    @Transactional
    public String confirmDraft(Long managerId, Long draftId) {
        Employee existManager = employeeService.findByID(managerId).get();
        LeaveDraft unconfirmedDraft = leaveDraftRepository.findById(draftId).get();
        //Must not be rejected or approved by the manager
        if(unconfirmedDraft.getIsValid() != null) {
            return unconfirmedDraft.getIsValid() ? "Draft is confirmed" : "Draft is not valid";
        }

        Employee employee = unconfirmedDraft.getEmployee();
       // Must be in the same department with the employee for the manager to approve
        if(employee.getDepartment() != existManager.getDepartment()) {
            return "Wrong Department";
        }
        //The number of days off should not be greater than 5 and the employee must have sufficient leave.
        if(unconfirmedDraft.getLeave_days() > 5 || unconfirmedDraft.getLeave_days() > employee.getLeaves_number()) {
            unconfirmedDraft.setIsValid(false);
            return "Leaves Days is not valid";
        }
        //If the conditions are met, the manager approves the permission draft.
        unconfirmedDraft.setIsValid(true);
        employee.setLeaves_number(employee.getLeaves_number() - unconfirmedDraft.getLeave_days());

        return "Confirmation is success";
    }

    /**
     * Employee displays approved drafts
      */
    @Override
    public List<LeaveDraft> getConfirmedDraft(Long employeeId) {
        Employee existEmployee = employeeService.findByID(employeeId).get();

        return existEmployee.getDrafts()
                .stream()
                .filter(LeaveDraft::getIsValid)
                .collect(Collectors.toList());
    }

}
