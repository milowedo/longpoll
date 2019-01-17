package com.restController;

import com.LongPolling.RequestPromise;
import com.entity.Employee;
import com.LongPolling.Overseer;
import com.exceptionHandlingStuff.EmployeeNotFoundException;
import com.services.EmployeeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

    private final EmployeeServiceInterface employeeService;
    private final Overseer overseer;

    @Autowired
    public EmployeeRestController(EmployeeServiceInterface employeeService, Overseer overseer) {
        this.employeeService = employeeService;
        this.overseer = overseer;
    }

    @GetMapping("/subscribe")
    public RequestPromise handleAsync(HttpSession session){
        RequestPromise output = new RequestPromise(employeeService);
        output.setSession(session);
        overseer.subscribe(output);
        return output;
    }

    @GetMapping("/trigger/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable int employeeId){
        Employee temp = employeeService.getEmployee(employeeId);
        employeeService.saveEmployee(temp);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //GET EMPLOYEE BY ID
    @GetMapping("/employee/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId){
        Employee returnedEmployee = employeeService.getEmployee(employeeId);
//        session.invalidate();
        if(returnedEmployee !=null){
            return returnedEmployee;
        }
        else throw new EmployeeNotFoundException("Employee not found: " + employeeId);
    }


}
