package com.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.entity.Employee;

@Repository
public interface EmployeeRepositry extends JpaRepository<Employee, Integer>{

	public  Optional<Employee>findByUserName(String userName);
}
