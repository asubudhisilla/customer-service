package com.assignment.customer.controller;


import com.assignment.customer.configuration.CustomerConfiguration;
import com.assignment.customer.model.Customer;
import com.assignment.customer.service.impl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({CustomerConfiguration.class})
@ActiveProfiles("test")
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceImpl customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenCustomerObject_whenCreateEmployee_thenReturnSavedCustomer() throws Exception{

        // given - precondition or setup
        Customer customer = Customer.builder()
                .firstName("A")
                .lastName("B")
                .dob(LocalDate.of(1990,4,6))
                .address("Pune")
                .build();
        given(customerService.createCustomer(any(Customer.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",
                        is(customer.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(customer.getLastName())));
    }

    // JUnit test for Get All customers REST API
    @Test
    public void givenListOfCustomer_whenGetAllCustomers_thenReturnCustomerList() throws Exception{
        // given - precondition or setup
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = Customer.builder()
                .firstName("A")
                .lastName("B")
                .dob(LocalDate.of(1990,4,6))
                .address("Pune")
                .build();
        Customer customer2 = Customer.builder()
                .firstName("A")
                .lastName("c")
                .dob(LocalDate.of(1990,4,6))
                .address("Pune")
                .build();
        customers.add(customer1);
        customers.add(customer2);
        given(customerService.getAllCustomer()).willReturn(customers);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/customer"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(customers.size())));

    }
}
