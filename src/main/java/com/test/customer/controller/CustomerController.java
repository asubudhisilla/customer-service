package com.test.customer.controller;

import com.test.customer.model.Customer;
import com.test.customer.payload.RequestCustomerDto;
import com.test.customer.payload.ResponseCustomerDto;
import com.test.customer.service.ICustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private ICustomerService customerService;
    @Autowired
    private ModelMapper mapper;

    public CustomerController(ICustomerService customerService) {
        super();
        this.customerService = customerService;
    }

    @GetMapping
    public List<ResponseCustomerDto> getAllCustomers() {
        return customerService.getAllCustomer().stream().map(customer -> convertCustomerToResponseCustomerDto(customer)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCustomerDto> getCustomerById(@PathVariable(name = "id") UUID id) {
        Customer customer = customerService.getCustomerById(id);
        // convert entity to DTO
        ResponseCustomerDto customerResponse = convertCustomerToResponseCustomerDto(customer);
        return ResponseEntity.ok().body(customerResponse);
    }

    @PostMapping
    public ResponseEntity<ResponseCustomerDto>  saveCustomer(@RequestBody RequestCustomerDto requestCustomerDto){
        Customer requestCustomer = convertRequestCustomerDtoToCustomer(requestCustomerDto);
        Customer customer = customerService.createCustomer(requestCustomer);
        ResponseCustomerDto customerResponse = convertCustomerToResponseCustomerDto(customer);
        return ResponseEntity.ok().body(customerResponse);
    }

    private ResponseCustomerDto convertCustomerToResponseCustomerDto(Customer customer) {
        ResponseCustomerDto responseCustomerDto = mapper.map(customer, ResponseCustomerDto.class);
        responseCustomerDto.setAge(getAge(customer.getDob()));
        return responseCustomerDto;
    }

    private Customer convertRequestCustomerDtoToCustomer(RequestCustomerDto requestCustomerDto) {
        return this.mapper.map(requestCustomerDto, Customer.class);
    }

    private RequestCustomerDto convertCustomerToRequestCustomerDto(Customer customer) {
        return this.mapper.map(customer, RequestCustomerDto.class);
    }

    private long getAge(LocalDate dob) {
        return ChronoUnit.YEARS.between(dob, LocalDate.now());
    }
}
