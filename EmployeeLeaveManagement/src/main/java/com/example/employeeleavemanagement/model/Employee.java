package com.example.employeeleavemanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "employees")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializier","handler"})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employee_name;
    private Double employee_salary;
    private String username;
    private String password;
    private Integer employee_age;
    private String employee_img;
    private Integer leaves_number;
    private Integer role;

//    @OneToOne(fetch = FetchType.EAGER, cascade =  CascadeType.ALL, mappedBy = "manager")
//    private Department department;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Department department;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "employee")
    @JsonIgnore
    private Set<LeaveDraft> drafts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Employee employee = (Employee) o;
        return id.equals(employee.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
