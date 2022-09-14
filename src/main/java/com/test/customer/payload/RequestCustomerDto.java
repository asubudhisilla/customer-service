package com.test.customer.payload;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RequestCustomerDto {

    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String address;
}
