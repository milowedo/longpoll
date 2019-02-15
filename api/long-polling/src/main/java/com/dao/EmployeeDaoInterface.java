package com.dao;

import java.util.List;

import com.entity.Employee;

public interface EmployeeDaoInterface {

	List<Employee> getAllEmployees();
	void saveEmployee(Employee theEmployee);
	Employee getEmployee(int theId);
	void deleteEmployee(int theId);
	
}
