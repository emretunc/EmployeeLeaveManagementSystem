package com.example.employeeleavemanagement.security;

import com.example.employeeleavemanagement.model.Employee;
import com.example.employeeleavemanagement.repository.EmployeeRepository;
import com.example.employeeleavemanagement.utilities.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthEmployeeService implements UserDetailsService {

    private  EmployeeRepository employeeRepository;

    @Autowired
    public AuthEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        return new org.springframework.security.core.userdetails.User(employee.getUsername(),
                employee.getPassword(),
                getAuthorities(employee.getRole()));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Integer roleID) {
        Collection<GrantedAuthority> authorities = null;
        if(roleID == 1) {
            authorities = AuthorityUtils.createAuthorityList(Roles.ROLE_ADMIN.name());
        } else if(roleID == 2) {
            authorities = AuthorityUtils.createAuthorityList(Roles.ROLE_MANAGER.name());
        } else if(roleID == 3) {
            authorities = AuthorityUtils.createAuthorityList(Roles.ROLE_EMPLOYEE.name());
        }
        return authorities;
    }


}
