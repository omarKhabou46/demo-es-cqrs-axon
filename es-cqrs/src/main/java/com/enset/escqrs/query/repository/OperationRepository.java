package com.enset.escqrs.query.repository;

import com.enset.escqrs.query.entity.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<AccountOperation, String> {
    List<AccountOperation> findAllByAccount_AccountId(String accountAccountId);
}
