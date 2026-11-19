package com.enset.escqrs.query.controller;

import com.enset.escqrs.query.entity.Account;
import com.enset.escqrs.query.querys.GetAllAccountQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final QueryGateway queryGateway;

    @GetMapping("/all")
    public CompletableFuture<List<Account>> getAllAccount() {
        CompletableFuture<List<Account>> response = queryGateway.query(new GetAllAccountQuery(), ResponseTypes.multipleInstancesOf(Account.class));
        return response;
    }
}
