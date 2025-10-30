package com.project.tour.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tour.Entity.AccountRole;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, String> {
}
