package com.assignment.customer.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "customer_details")
@NoArgsConstructor
public class Customer{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    UUID id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "date_of_birth")
    LocalDate dob;

    @Column(name = "address")
    String address;

    @CreationTimestamp
    LocalDate createdAt;

    @UpdateTimestamp
    LocalDate updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return id != null && Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
