package com.services;

import com.entity.Employee;
import com.entity.Resolvable;

import java.util.List;
import java.util.Optional;

public interface EmployeeServiceInterface extends ServiceInterface{

    void deleteEmployee(int theId);
    List<Employee> getAllEmployees();
    void saveEmployee(Employee theEmployee);
    Employee getEmployee(int userId);
}

