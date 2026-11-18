package com.enset.escqrs.commands.controllers;

import com.enset.escqrs.commands.commands.AddAccountCommand;
import com.enset.escqrs.commands.dtos.AddNewAccountRequestDTO;
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

    @GetMapping("/events/{accountId}")
    public Stream<?> eventsStream(@PathVariable String accountId) {
        return eventStore.readEvents(accountId).asStream();
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }
}
