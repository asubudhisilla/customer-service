package com.test.customer.service.impl;

import com.test.customer.exception.CustomerExistException;
import com.test.customer.exception.CustomerNotFoundException;
import com.test.customer.model.Customer;
import com.test.customer.repository.ICustomerRepository;
import com.test.customer.service.ICustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepository;

    public CustomerServiceImpl(ICustomerRepository customerRepository){
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.searchByFirstAndLastName(customer.getFirstName(),customer.getFirstName());
        if(existingCustomer.isPresent()) {
            throw new CustomerExistException(String.format("Customer already exists with given firstName=%s and lastName=%s", customer.getFirstName(), customer.getLastName()));
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomerAddress(UUID uuid, String address) {
        Customer customer = customerRepository.findById(uuid).orElseThrow(()-> new CustomerNotFoundException(String.format("Customer not found for id=%s",uuid)));
        customer.setAddress(address);
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(UUID uuid) {
        return customerRepository.findById(uuid).orElseThrow(()-> new CustomerNotFoundException(String.format("Customer not found for id=%s",uuid)));
    }

    @Override
    public List<Customer> searchByFirstAndOrLastName(Optional<String> firstName, Optional<String> lastName) {
        return customerRepository.searchByFirstAndOrLastName(firstName, lastName);
    }
}
