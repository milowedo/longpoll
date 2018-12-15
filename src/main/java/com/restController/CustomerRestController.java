package com.restController;

import com.LongPolling.HangingRequest;
import com.LongPolling.RequestPromise;
import com.entity.Customer;
import com.LongPolling.Overseer;
import com.exceptionHandlingStuff.CustomerNotFoundException;
import com.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class CustomerRestController{

    private final ServiceInterface customerService;
    private final Overseer overseer;

    @Autowired
    public CustomerRestController(ServiceInterface customerService, Overseer overseer) {
        this.customerService = customerService;
        this.overseer = overseer;
    }

    @GetMapping("/subscribe")
    public RequestPromise handleAsync(HttpSession session){
        RequestPromise output = new RequestPromise(customerService);
        output.setSession(session);
        overseer.subscribe(output);

        System.out.println(Thread.currentThread());
        return output;
    }

    @GetMapping("/trigger/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable int customerId){
        Customer temp = customerService.getCustomer(customerId);
        customerService.saveCustomer(temp);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/customer/{customerId}")
    public Customer getCustomer( @PathVariable int customerId){
        Customer returnedCustomer = customerService.getCustomer(customerId);
//        session.invalidate();
        if(returnedCustomer!=null){
            return returnedCustomer;
        }
        else throw new CustomerNotFoundException("Customer not found: " + customerId);
    }

//
//    @GetMapping("/blocking")
//    public List<Customer> getStudent(){
//        return customerService.getCustomers();
//    }
//

//
//    @PostMapping("/blocking")
//    public Customer addCustomer(@RequestBody Customer customer){
//        customer.setId(0);
//        customerService.saveCustomer(customer);
//        return customer;
//    }
//
//    @DeleteMapping("/blocking/{customerId}")
//    public String deleteCustomer(@PathVariable int customerId){
//
//        Customer tempCustomer = customerService.getCustomer(customerId);
//                if(tempCustomer == null)
//                    throw new CustomerNotFoundException("There is no such customer with id:" + customerId);
//
//        customerService.deleteCustomer(customerId);
//        return "Deleted customer id: " + customerId;
//    }
}
