package com.test.customer.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
public class ResponseCustomerDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private Long age;
    private String address;
}
