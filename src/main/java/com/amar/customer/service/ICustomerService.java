package com.amar.customer.service;

import com.amar.customer.model.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICustomerService {

    Customer createCustomer(Customer customer);
    List<Customer> getAllCustomer();
    Customer updateCustomerAddress(UUID uuid, String address);
    Customer getCustomerById(UUID uuid);
    List<Customer> searchByFirstAndOrLastName(Optional<String> firstName, Optional<String> lastName);
}
