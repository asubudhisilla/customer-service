package com.amar.customer.payload;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ResponseCustomerDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String address;
}
