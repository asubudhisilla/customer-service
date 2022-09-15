package com.assignment.customer.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class ResponseCustomerDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private Long age;
    private String address;
}
