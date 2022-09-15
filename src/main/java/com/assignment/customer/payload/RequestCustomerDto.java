package com.assignment.customer.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class RequestCustomerDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private LocalDate dob;
    private String address;


}
