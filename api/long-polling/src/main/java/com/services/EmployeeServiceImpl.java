package com.services;
import com.entity.Employee;
import com.dao.EmployeeDaoInterface;
import com.entity.Resolvable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl extends ServicePoll implements EmployeeServiceInterface {

    private final EmployeeDaoInterface employeeDaoInterface;

    @Autowired
    public EmployeeServiceImpl(EmployeeDaoInterface employeeDaoInterface) {
        this.employeeDaoInterface = employeeDaoInterface;
    }

    @Override
    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeDaoInterface.getAllEmployees();
    }

    @Override
    @Transactional
    public void saveEmployee(Employee theEmployee) {
        employeeDaoInterface.saveEmployee(theEmployee);
        this.notifyOfChange(theEmployee);
    }

    @Override
    @Transactional
    public Employee getEmployee(int theId) {
        return employeeDaoInterface.getEmployee(theId);
    }

    @Override
    @Transactional
    public void deleteEmployee(int theId) {
        employeeDaoInterface.deleteEmployee(theId);
    }

}
