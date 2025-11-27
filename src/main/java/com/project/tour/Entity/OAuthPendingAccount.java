package com.project.tour.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "oauth_pending_accounts")
public class OAuthPendingAccount {

    @Id
    @Column(name = "pending_id", nullable = false, updatable = false)
    private String pendingId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "provider", nullable = false)
    private String provider; // e.g., google

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "token_expiry", nullable = false)
    private LocalDateTime tokenExpiry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private AccountRole role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.pendingId == null) {
            this.pendingId = "PEN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
