package com.project.tour.Entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_email", nullable = false, unique = true)
    private String customerEmail;

    @Column(name = "customer_phone", nullable = false, unique = true)
    private String customerPhone;

    @Column(name = "customer_address", nullable = false)
    private String customerAddress;

    @Column(name = "customer_date_of_birth", nullable = false)
    private LocalDate customerDateOfBirth;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
