package com.amar.customer.respository;

import com.amar.customer.configuration.DataSourceConfig;
import com.amar.customer.configuration.TestDataSourceConfig;
import com.amar.customer.model.Customer;
import com.amar.customer.repository.ICustomerRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({DataSourceConfig.class, TestDataSourceConfig.class})
@ActiveProfiles("test")
public class CustomerRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ICustomerRepository repository;

    @Test
    @Order(1)
    public void should_find_no_customers_if_repository_is_empty() {
        Iterable<Customer> customers = repository.findAll();

        assertThat(customers).isEmpty();
    }

    @Test
    @Order(2)
    public void should_store_a_customer() {

        Customer customer = repository.save(Customer.builder()
                .firstName("Amar")
                .lastName("Silla")
                .dob(LocalDate.of(1990,06,04))
                .address("T3A304, Pune")
                .build());

        assertThat(customer).hasFieldOrPropertyWithValue("firstName", "Amar");
        assertThat(customer).hasFieldOrPropertyWithValue("lastName", "Silla");
        assertThat(customer).hasFieldOrPropertyWithValue("dob", LocalDate.of(1990,06,04));
        assertThat(customer).hasFieldOrPropertyWithValue("address", "T3A304, Pune");
    }
}
