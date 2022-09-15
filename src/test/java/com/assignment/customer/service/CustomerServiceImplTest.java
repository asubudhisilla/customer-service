package com.assignment.customer.service;

import com.assignment.customer.exception.CustomerExistException;
import com.assignment.customer.exception.CustomerNotFoundException;
import com.assignment.customer.model.Customer;
import com.assignment.customer.repository.ICustomerRepository;
import com.assignment.customer.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private ICustomerRepository customerRepository;

    private CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    public void setup() {
        customer = Customer.builder().firstName("Amar").lastName("Silla").dob(LocalDate.of(1990, 6, 4)).address("Pune").id(UUID.randomUUID()).build();
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void givenNonExistingCustomer_whenSaveCustomer_thenReturnCustomer() {

        when(customerRepository.searchByFirstAndLastName(any(String.class), any(String.class))).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        // when -  action or the behaviour that we are going test
        Customer savedCustomer = customerService.createCustomer(customer);

        // then - verify the output
        assertThat(savedCustomer).isNotNull();
    }

    @Test
    public void givenExistingCustomer_whenSaveCustomer_thenThrowsException() {

        when(customerRepository.searchByFirstAndLastName(any(String.class), any(String.class))).thenReturn(Optional.of(customer));
        org.junit.jupiter.api.Assertions.assertThrows(CustomerExistException.class, () -> customerService.createCustomer(customer));
    }

    @Test
    public void givenCustomerList_whenGetAllCustomer_thenReturnConsumerList() {

        when(customerRepository.findAll()).thenReturn(List.of(customer));
        // when -  action or the behaviour that we are going test
        List<Customer> customerList = customerService.getAllCustomer();

        // then - verify the output
        assertThat(customerList).isNotNull();
        assertThat(customerList.size()).isEqualTo(1);
    }

    @Test
    public void givenEmptyCustomerList_whenGetAllCustomer_thenReturnEmptyConsumerList() {

        when(customerRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        // when -  action or the behaviour that we are going test
        List<Customer> customerList = customerService.getAllCustomer();

        // then - verify the output
        assertThat(customerList).isEmpty();
        assertThat(0).isEqualTo(0);
    }

    @Test
    public void givenCustomerId_whenGetCustomerById_thenReturnConsumer() {

        when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.of(customer));
        // when -  action or the behaviour that we are going test
        Customer savedCustomer = customerService.getCustomerById(customer.getId());
        // then - verify the output
        assertThat(savedCustomer).isNotNull();
    }

    @Test
    public void givenMissingCustomerId_whenGetCustomerById_thenThrowsException() {
        when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        // when -  action or the behaviour that we are going test                               
        org.junit.jupiter.api.Assertions.assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(customer.getId()));
    }
}
