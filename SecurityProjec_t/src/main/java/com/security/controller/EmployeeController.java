package com.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.RegisterRequest;
import com.security.entity.Employee;
import com.security.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
private final EmployeeService employeeService;
	
@PostMapping("/register")
   public Employee register(@RequestBody RegisterRequest registerRequest) {
	   Employee emp=new Employee();
	   emp.setUserName(registerRequest.userName());
	   emp.setRole(registerRequest.role());
	   emp.setPassword(registerRequest.password());
	return employeeService.register(emp);
	   
   }
}