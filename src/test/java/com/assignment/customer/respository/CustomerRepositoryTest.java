package com.assignment.customer.respository;

import com.assignment.customer.model.Customer;
import com.assignment.customer.repository.ICustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {
    @Autowired
    private ICustomerRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_find_no_customers_if_repository_is_empty() {
        Iterable<Customer> customers = repository.findAll();
        assertThat(customers).isEmpty();
    }

    @Test
    public void should_store_a_customer() {
        Customer customer = repository.save(Customer.builder()
                .firstName("Amar")
                .lastName("Silla")
                .dob(LocalDate.of(1990, 6, 4))
                .address("T3A304, Pune")
                .build());

        assertThat(customer).hasFieldOrPropertyWithValue("firstName", "Amar");
        assertThat(customer).hasFieldOrPropertyWithValue("lastName", "Silla");
        assertThat(customer).hasFieldOrPropertyWithValue("dob", LocalDate.of(1990, 6, 4));
        assertThat(customer).hasFieldOrPropertyWithValue("address", "T3A304, Pune");
    }

    @Test
    public void should_find_all_customers() {
        entityManager.persist(Customer.builder()
                .firstName("Amar")
                .lastName("Silla")
                .dob(LocalDate.of(1990, 6, 4))
                .address("T3A304, Pune")
                .build());
        List<Customer> customers = repository.findAll();
        assertThat(customers.size()).isGreaterThan(0);
    }

    @Test
    public void should_find_customer_by_id() {
        Customer c = entityManager.persist(Customer.builder()
                .firstName("Amar")
                .lastName("Silla")
                .dob(LocalDate.of(1990, 6, 4))
                .address("T3A304, Pune")
                .build());
        Optional<Customer> customer = repository.findById(c.getId());
        assertTrue(customer.isPresent());
        assertThat(customer.get().getId()).isEqualTo(c.getId());
    }

    @Test
    public void should_find_customer_by_FirstName() {
        entityManager.persist(Customer.builder()
                .firstName("Amar")
                .lastName("Silla")
                .dob(LocalDate.of(1990, 6, 4))
                .address("T3A304, Pune")
                .build());
        List<Customer> customers = repository.searchByFirstAndOrLastName("Amar", null);
        assertThat(customers.size()).isGreaterThan(0);
    }

    @Test
    public void should_find_customer_by_LastName() {
        entityManager.persist(Customer.builder()
                .firstName("Amar")
                .lastName("Silla")
                .dob(LocalDate.of(1990, 6, 4))
                .address("T3A304, Pune")
                .build());
        List<Customer> customers = repository.searchByFirstAndOrLastName(null, "Silla");
        assertThat(customers.size()).isGreaterThan(0);
    }

    @Test
    public void should_find_customer_by_FirstNameAndLastName() {
        entityManager.persist(Customer.builder()
                .firstName("Amar")
                .lastName("Silla")
                .dob(LocalDate.of(1990, 6, 4))
                .address("T3A304, Pune")
                .build());
        List<Customer> customers = repository.searchByFirstAndOrLastName("Amar", "Silla");
        assertThat(customers.size()).isGreaterThan(0);
    }

    @Test
    public void should_find_all_customers_ifFirstNameAndLastName_isEmpty() {
        entityManager.persist(Customer.builder()
                .firstName("Amar")
                .lastName("Silla")
                .dob(LocalDate.of(1990, 6, 4))
                .address("T3A304, Pune")
                .build());
        entityManager.persist(Customer.builder()
                .firstName("Ronit")
                .lastName("Silla")
                .dob(LocalDate.of(1995, 11, 23))
                .address("T3A304, Pune")
                .build());
        List<Customer> customers = repository.searchByFirstAndOrLastName(null, null);
        assertThat(customers.size()).isEqualTo(2);
    }
}
