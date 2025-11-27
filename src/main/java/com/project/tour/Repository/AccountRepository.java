package com.project.tour.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.tour.Entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsByUsername(String username);
    @Query("SELECT a FROM Account a JOIN FETCH a.role WHERE a.username = :username")
    Optional<Account> findByUsernameWithRole(@Param("username") String username);
    Optional<Account> findByUsername(String username);
    Optional<Account> findByVerificationToken(String verificationToken);
}
