package com.enset.escqrs.query.handlers;

import com.enset.escqrs.query.entity.Account;
import com.enset.escqrs.query.querys.GetAllAccountQuery;
import com.enset.escqrs.query.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountQueryHandler {

    private final AccountRepository accountRepository;

    @QueryHandler
    public List<Account> getAllAccounts(GetAllAccountQuery getAllAccountQuery) {
        return accountRepository.findAll();
    }
}
