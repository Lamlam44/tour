package com.project.tour.Repository;

import com.project.tour.Entity.OAuthPendingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthPendingAccountRepository extends JpaRepository<OAuthPendingAccount, String> {
    Optional<OAuthPendingAccount> findByToken(String token);
    boolean existsByEmailAndProvider(String email, String provider);
    Optional<OAuthPendingAccount> findByEmailAndProvider(String email, String provider);
}
