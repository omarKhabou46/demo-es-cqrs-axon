package com.enset.escqrs.query.repository;

import com.enset.escqrs.query.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
