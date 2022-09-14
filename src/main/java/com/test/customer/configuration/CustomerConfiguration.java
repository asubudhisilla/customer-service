package com.test.customer.configuration;

import org.springframework.context.annotation.Bean;

public class CustomerConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
