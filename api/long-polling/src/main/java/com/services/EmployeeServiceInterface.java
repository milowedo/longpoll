package com.services;

import com.LongPolling.ServiceInterface;
import com.entity.Employee;

import java.util.List;

public interface EmployeeServiceInterface extends ServiceInterface {

    void deleteEmployee(int theId);
    List<Employee> getAllEmployees();
    void saveEmployee(Employee theEmployee);
    Employee getEmployee(int userId);
}

