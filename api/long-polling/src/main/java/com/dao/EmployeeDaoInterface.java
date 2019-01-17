package com.dao;

import java.util.List;

import com.entity.Employee;

public interface EmployeeDaoInterface {

	public List<Employee> getAllEmployees();

	public void saveEmployee(Employee theEmployee);

	public Employee getEmployee(int theId);

	public void deleteEmployee(int theId);
	
}
