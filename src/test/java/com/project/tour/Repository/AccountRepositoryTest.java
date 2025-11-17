package com.project.tour.Repository;

import javax.swing.text.html.parser.Entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.project.tour.Entity.Account;

import jakarta.persistence.EntityManager;

@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EntityManager entityManager;
    @BeforeEach
    void setUp(){
        Account acc = new Account();
        acc.setUsername("testuser");
        acc.setPassword("password123");
        entityManager.persist(acc);
        entityManager.flush();
    }
    @Test
    public void testFindByUsername(){
        Account found = accountRepository.findByUsername("testuser").orElse(null);
        assert(found != null);
        assert(found.getUsername().equals("testuser"));

    }
}
