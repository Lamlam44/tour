package com.project.tour.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tour.Entity.Account;
import com.project.tour.Entity.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsByCustomerEmail(String customerEmail);
    boolean existsByCustomerPhone(String customerPhone);
    Optional<Customer> findByAccount(Account account);
    List<Customer> findByCustomerNameContainingIgnoreCase(String customerName);
}
