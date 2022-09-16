package com.assignment.customer.controller;

import com.assignment.customer.model.Customer;
import com.assignment.customer.service.ICustomerService;
import com.assignment.customer.payload.RequestCustomerDto;
import com.assignment.customer.payload.ResponseCustomerDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
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
        logger.info(String.format("GET request with id=%s",id));
        Customer customer = customerService.getCustomerById(id);
        // convert entity to DTO
        ResponseCustomerDto customerResponse = convertCustomerToResponseCustomerDto(customer);
        return ResponseEntity.ok().body(customerResponse);
    }

    @PostMapping
    public ResponseEntity<ResponseCustomerDto>  saveCustomer(@Valid  @RequestBody RequestCustomerDto requestCustomerDto){
        logger.info("POST request for payload"+requestCustomerDto.toString());
        Customer requestCustomer = convertRequestCustomerDtoToCustomer(requestCustomerDto);
        Customer customer = customerService.createCustomer(requestCustomer);
        ResponseCustomerDto customerResponse = convertCustomerToResponseCustomerDto(customer);
        return ResponseEntity.ok().body(customerResponse);
    }

    @PutMapping("/updateAddress")
    public ResponseEntity<ResponseCustomerDto> updateCustomerAddress(@RequestParam(name = "id") UUID id, @RequestParam(name = "address") String address) {
        logger.info(String.format("PUT request to update address for id=%s and address=%s",id,address));
        Customer customer = customerService.updateCustomerAddress(id, address);
        // convert entity to DTO
        ResponseCustomerDto customerResponse = convertCustomerToResponseCustomerDto(customer);
        return ResponseEntity.ok().body(customerResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResponseCustomerDto>> searchByFirstAndOrLastName(@RequestParam(value = "firstName", required = false)String firstName, @RequestParam(value = "lastName", required = false) String lastName) {
        logger.info(String.format("Request to search customer by firstName=%s and/or lastName=%s",firstName,lastName));
        List<Customer> customers = customerService.searchByFirstAndOrLastName(firstName, lastName);
        // convert entity to DTO
        return ResponseEntity.ok().body(customers.stream().map(this::convertCustomerToResponseCustomerDto).collect(Collectors.toList()));
    }

    private ResponseCustomerDto convertCustomerToResponseCustomerDto(Customer customer) {
        ResponseCustomerDto responseCustomerDto = mapper.map(customer, ResponseCustomerDto.class);
        logger.debug(String.format("Age is %s for dob=%s",customer.getDob(),getAge(customer.getDob())));
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
