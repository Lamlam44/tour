package com.project.tour.Entity;

import java.util.Set;

import jakarta.persistence.*;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account_roles")
public class AccountRole {
    @Id
    @Column(name = "account_role_id", updatable = false, nullable = false)
    private String accountRoleId;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<Account> accounts;

    @PrePersist
    protected void onCreate() {
        if (this.accountRoleId == null) {
            generateId();
        }
    }

    public void generateId() {
        if (this.accountRoleId == null) {
            this.accountRoleId = "ROL-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
        }
    }
}
