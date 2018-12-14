package com.service;

import com.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface ServiceInterface {

    void deleteCustomer(int theId);

    Optional<Customer> resolve();

    List<Customer> getCustomers();

    void saveCustomer(Customer theCustomer);

    Customer getCustomer(int userId);

    void notifyOfChange();
}

