package com.project.tour.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tour.Entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsByCustomerEmail(String customerEmail);
    boolean existsByCustomerPhone(String customerPhone);
}
