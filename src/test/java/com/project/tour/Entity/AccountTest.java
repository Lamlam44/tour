package com.project.tour.Entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AccountTest {
    @Test
    public void testAccountGetterSetter(){
        Account acc = new Account();
        acc.setUsername("testuser");
        acc.setPassword("password123");
        AccountRole role = new AccountRole();
        role.setRoleName("ROLE_USER");
        acc.setRole(role);

        assertEquals("testuser", acc.getUsername());
        assertEquals("password123", acc.getPassword());
        assertEquals("ROLE_USER", acc.getRole().getRoleName());
    }
}
