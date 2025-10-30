package com.project.tour.Entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "account_id", nullable = false, updatable = false)
    private String accountId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "account_created_at", nullable = false)
    private LocalDateTime accountCreatedAt;

    @Column(name = "account_updated_at", nullable = false)
    private LocalDateTime accountUpdatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private AccountRole role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private Set<Invoice> invoices;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
    private Customer customer;

    @PrePersist
    protected void onCreate() {
        if (this.accountId == null) {
            generateId();
        }
        LocalDateTime now = LocalDateTime.now();
        accountCreatedAt = now;
        accountUpdatedAt = now;
    }

    public void generateId() {
        if (this.accountId == null) {
            this.accountId = "ACC-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
        }
    }
    @PreUpdate
    protected void onUpdate() {
        accountUpdatedAt = LocalDateTime.now();
    }
}
