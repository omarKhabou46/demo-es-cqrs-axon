package com.enset.escqrs.commands.controllers;

import com.enset.escqrs.commands.commands.AddAccountCommand;
import com.enset.escqrs.commands.dtos.AddNewAccountRequestDTO;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")
@RequiredArgsConstructor
public class AccountCommandController {

   private final CommandGateway commandGateway;

    @PostMapping("/add")
    public CompletableFuture<AddAccountCommand>  createAccount(@RequestBody AddNewAccountRequestDTO request) {
        CompletableFuture<AddAccountCommand> result = commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        ));
        return result;
    }
}
