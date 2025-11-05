package com.project.tour.Entity;

import java.util.Set;

import jakarta.persistence.*;

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
    @Column(length = 10)
    private String accountRoleId;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<Account> accounts;
}
