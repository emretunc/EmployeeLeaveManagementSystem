package com.example.employeeleavemanagement.service;

import com.example.employeeleavemanagement.model.LeaveDraft;

import java.util.List;

public interface LeaveDraftService {
    public LeaveDraft create(Long userId);
    public LeaveDraft update(Long userId, Long id, LeaveDraft leaveDraft);
    public String delete(Long userId, Long draftId);
    public String confirmDraft(Long managerId, Long draftId);
    public List<LeaveDraft> getConfirmedDraft(Long employeeId);
}
