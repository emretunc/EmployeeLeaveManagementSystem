package com.example.employeeleavemanagement.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "draft")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializier","handler"})
public class LeaveDraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isValid;
    private Integer leave_days = 5;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Employee employee;
}
