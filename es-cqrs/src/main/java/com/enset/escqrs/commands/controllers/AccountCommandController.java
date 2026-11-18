package com.enset.escqrs.commands.controllers;

import com.enset.escqrs.commands.commands.AddAccountCommand;
import com.enset.escqrs.commands.commands.CreditAccountCommand;
import com.enset.escqrs.commands.commands.UpdateStatusCommand;
import com.enset.escqrs.commands.dtos.AddNewAccountRequestDTO;
import com.enset.escqrs.commands.dtos.CreditAccountDTO;
import com.enset.escqrs.commands.dtos.UpdateAccountDTO;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
@RequiredArgsConstructor
public class AccountCommandController {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    @PostMapping("/add")
    public CompletableFuture<String> createAccount(@RequestBody AddNewAccountRequestDTO request) {
        CompletableFuture<String> result = commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        ));
        return result;
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountDTO creditAccountDTO) {
        CompletableFuture<String> result = commandGateway.send(new CreditAccountCommand(
                creditAccountDTO.accountId(),
                creditAccountDTO.amount(),
                creditAccountDTO.currency()
        ));

        return result;
    }

    @PutMapping("/updateStatus")
    public CompletableFuture<String> creditAccount(@RequestBody UpdateAccountDTO updateAccountDTO) {
        CompletableFuture<String> result = commandGateway.send(new UpdateStatusCommand(
              updateAccountDTO.accountId(), updateAccountDTO.status()
        ));

        return result;
    }

    @GetMapping("/events/{accountId}")
    public Stream<?> eventsStream(@PathVariable String accountId) {
        return eventStore.readEvents(accountId).asStream();
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
