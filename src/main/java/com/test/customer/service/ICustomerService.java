package com.test.customer.service;

import com.test.customer.model.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICustomerService {

    Customer createCustomer(Customer customer);
    List<Customer> getAllCustomer();
    Customer updateCustomerAddress(UUID uuid, String address);
    Customer getCustomerById(UUID uuid);
    List<Customer> searchByFirstAndOrLastName(String firstName, String lastName);
}
