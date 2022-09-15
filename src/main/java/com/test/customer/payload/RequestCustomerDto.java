package com.test.customer.payload;

import lombok.*;

import java.time.LocalDate;

@Data
public class RequestCustomerDto {
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String address;
}
