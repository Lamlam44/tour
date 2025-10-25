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
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Invoice invoice;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
    private Customer customer;

    @PrePersist
    protected void onCreate() {
        accountCreatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        accountUpdatedAt = LocalDateTime.now();
    }
}
