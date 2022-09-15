package com.test.customer.controller;

import com.test.customer.model.Customer;
import com.test.customer.payload.RequestCustomerDto;
import com.test.customer.payload.ResponseCustomerDto;
import com.test.customer.service.ICustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final ICustomerService customerService;
    @Autowired
    private ModelMapper mapper;

    public CustomerController(ICustomerService customerService) {
        super();
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseCustomerDto>> getAllCustomers() {
        return ResponseEntity.ok().body(customerService.getAllCustomer().stream().map(this::convertCustomerToResponseCustomerDto).collect(Collectors.toList()));
    }

    @GetMapping("/id")
    public ResponseEntity<ResponseCustomerDto> getCustomerById(@RequestParam(name = "id") UUID id) {
        Customer customer = customerService.getCustomerById(id);
        // convert entity to DTO
        ResponseCustomerDto customerResponse = convertCustomerToResponseCustomerDto(customer);
        return ResponseEntity.ok().body(customerResponse);
    }

    @PostMapping
    public ResponseEntity<ResponseCustomerDto>  saveCustomer(@Valid  @RequestBody RequestCustomerDto requestCustomerDto){
        Customer requestCustomer = convertRequestCustomerDtoToCustomer(requestCustomerDto);
        Customer customer = customerService.createCustomer(requestCustomer);
        ResponseCustomerDto customerResponse = convertCustomerToResponseCustomerDto(customer);
        return ResponseEntity.ok().body(customerResponse);
    }

    @PutMapping("/updateAddress")
    public ResponseEntity<ResponseCustomerDto> updateCustomerAddress(@RequestParam(name = "id") UUID id, @RequestParam(name = "address") String address) {
        Customer customer = customerService.updateCustomerAddress(id, address);
        // convert entity to DTO
        ResponseCustomerDto customerResponse = convertCustomerToResponseCustomerDto(customer);
        return ResponseEntity.ok().body(customerResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResponseCustomerDto>> searchByFirstAndOrLastName(@RequestParam(value = "firstName", required = false)String firstName, @RequestParam(value = "lastName", required = false) String lastName) {
        List<Customer> customers = customerService.searchByFirstAndOrLastName(firstName, lastName);
        // convert entity to DTO
        return ResponseEntity.ok().body(customers.stream().map(this::convertCustomerToResponseCustomerDto).collect(Collectors.toList()));
    }

    private ResponseCustomerDto convertCustomerToResponseCustomerDto(Customer customer) {
        ResponseCustomerDto responseCustomerDto = mapper.map(customer, ResponseCustomerDto.class);
        responseCustomerDto.setAge(getAge(customer.getDob()));
        return responseCustomerDto;
    }

    private Customer convertRequestCustomerDtoToCustomer(RequestCustomerDto requestCustomerDto) {
        return this.mapper.map(requestCustomerDto, Customer.class);
    }

    private long getAge(LocalDate dob) {
        return (dob==null? 0:ChronoUnit.YEARS.between(dob, LocalDate.now()));
    }
}
