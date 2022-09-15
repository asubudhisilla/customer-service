package com.assignment.customer.service;

import com.assignment.customer.model.Customer;
import java.util.List;
import java.util.UUID;

public interface ICustomerService {

    Customer createCustomer(Customer customer);
    List<Customer> getAllCustomer();
    Customer updateCustomerAddress(UUID uuid, String address);
    Customer getCustomerById(UUID uuid);
    List<Customer> searchByFirstAndOrLastName(String firstName, String lastName);
}
