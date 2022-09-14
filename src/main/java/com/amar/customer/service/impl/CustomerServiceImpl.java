package com.amar.customer.service.impl;

import com.amar.customer.exception.CustomerExistException;
import com.amar.customer.exception.CustomerNotFoundException;
import com.amar.customer.model.Customer;
import com.amar.customer.repository.ICustomerRepository;
import com.amar.customer.service.ICustomerService;
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
