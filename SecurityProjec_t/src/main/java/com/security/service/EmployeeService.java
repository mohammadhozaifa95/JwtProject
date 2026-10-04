package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.entity.Employee;
import com.security.repo.EmployeeRepositry;

@Service
public class EmployeeService implements UserDetailsService {
@Autowired
 private  	PasswordEncoder passwordEncoder;

@Autowired
 private 	EmployeeRepositry employeeRepositry;

	public Employee register(Employee employee) {
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		return employeeRepositry.save(employee);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepositry.findByUserName(username).orElseThrow();
		return User.builder().username(employee.getUserName()).roles(employee.getRole().name())
				.password(employee.getPassword()).build();
	}

}
