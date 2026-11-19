package com.enset.escqrs.commands.commands;

import com.enset.escqrs.enumirations.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor @Getter @NoArgsConstructor
public class UpdateStatusCommand {
    @TargetAggregateIdentifier
    private String accountId;
    private AccountStatus status;
}
