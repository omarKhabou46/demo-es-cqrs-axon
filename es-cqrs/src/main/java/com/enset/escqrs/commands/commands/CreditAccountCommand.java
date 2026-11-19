package com.enset.escqrs.commands.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter @AllArgsConstructor @NoArgsConstructor
public class CreditAccountCommand {
    @TargetAggregateIdentifier
    private String accountId;
    private double amount;
    private String currency;
}
