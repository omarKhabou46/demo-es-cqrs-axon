package com.enset.escqrs.query.repository;

import com.enset.escqrs.query.entity.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<AccountOperation, String> {
}
