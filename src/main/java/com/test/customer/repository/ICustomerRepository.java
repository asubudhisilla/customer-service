package com.test.customer.repository;

import com.test.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, UUID> {
    @Query(value = "select * from customer_details  where (:firstName is null or first_name = :firstName)"
            + " and (:lastName is null or last_name = :lastName)", nativeQuery = true)
    List<Customer> searchByFirstAndOrLastName(@Param("firstName") String firstName,
                                              @Param("lastName") String lastName);

    @Query(value = "select * from customer_details  where first_name = :firstName"
            + " and last_name = :lastName", nativeQuery = true)
    Optional<Customer> searchByFirstAndLastName(@Param("firstName") String firstName,
                                            @Param("lastName") String lastName);
}
