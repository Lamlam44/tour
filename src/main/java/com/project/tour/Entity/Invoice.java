package com.project.tour.Entity;

import java.util.Set;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @Column(length = 10)
    private String invoiceId;

    @Column(name = "invoice_created_at", nullable = false)
    private LocalDateTime invoiceCreatedAt;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "invoice_promotions",
        joinColumns = @JoinColumn(name = "invoice_id"),
        inverseJoinColumns = @JoinColumn(name = "promotion_id")
    )
    private Set<Promotion> promotions;

    @PrePersist
    protected void onCreate() {
        invoiceCreatedAt = java.time.LocalDateTime.now();
    }
}
