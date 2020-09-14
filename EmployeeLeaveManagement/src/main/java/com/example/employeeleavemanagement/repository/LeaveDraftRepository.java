package com.example.employeeleavemanagement.repository;


import com.example.employeeleavemanagement.model.LeaveDraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveDraftRepository extends JpaRepository<LeaveDraft, Long> {
}
