package com.service;
import com.entity.Customer;
import com.dao.CustomerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ServiceInterface {

    private final CustomerDAO customerDAO;
    private boolean isNewCustomerAdded = false;

    @Autowired
    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Transactional
    public Optional<Customer> resolve() {
        if (!isNewCustomerAdded) {
            return Optional.empty();
        } else {
            CustomerService thisObj = this;

            Customer changedCustomer = getCustomer(5);
            System.out.println(changedCustomer.toString());

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            thisObj.isNewCustomerAdded = false;
                        }
                    },
                    500
            );
            return Optional.of(changedCustomer);
        }
    }

    @Override
    @Transactional
    public List<Customer> getCustomers() {
        return customerDAO.getCustomers();
    }

    @Override
    @Transactional
    public void saveCustomer(Customer theCustomer) {
        customerDAO.saveCustomer(theCustomer);
        this.notifyOfChange();
    }

    @Override
    @Transactional
    public Customer getCustomer(int theId) {
        return customerDAO.getCustomer(5);
    }

    @Override
    @Transactional
    public void deleteCustomer(int theId) {
        customerDAO.deleteCustomer(theId);
    }

    @Override
    public void notifyOfChange() {
        this.isNewCustomerAdded = true;
    }


}
