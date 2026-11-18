package com.enset.escqrs.commands.aggregate;

import com.enset.escqrs.commands.commands.AddAccountCommand;
import com.enset.escqrs.commands.commands.CreditAccountCommand;
import com.enset.escqrs.commands.commands.UpdateStatusCommand;
import com.enset.escqrs.enumirations.AccountStatus;
import com.enset.escqrs.events.AddAccountEvent;
import com.enset.escqrs.events.CreditAccountEvent;
import com.enset.escqrs.events.UpdateStatusEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Slf4j
@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("##################### created account event ######################");
        if (command.getInitialBalance() <= 0) throw new RuntimeException("balance must be positive");
        AggregateLifecycle.apply(new AddAccountEvent(
                command.getId(),
                command.getInitialBalance(),
                command.getCurrency(),
                AccountStatus.CREATED
        ));
    }

    @EventSourcingHandler
    public void on(AddAccountEvent addAccountEvent) {
        log.info("##################### created account event ######################");
        this.accountId = addAccountEvent.getAccountId();
        this.balance = addAccountEvent.getBalance();
        this.currency = addAccountEvent.getCurrency();
        this.status = addAccountEvent.getStatus();
    }

    @CommandHandler
    public void creditHanlder(CreditAccountCommand creditAccountCommand) {
        if (!status.equals(AccountStatus.ACTIVATED)) throw new RuntimeException("the account must be active");
        if (creditAccountCommand.getAmount() <= 0) throw new IllegalArgumentException("amount must be positive");
        AggregateLifecycle.apply(new CreditAccountEvent(
                creditAccountCommand.getAccountId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(CreditAccountEvent creditAccountEvent) {
        accountId = creditAccountEvent.getAccountId();
        balance += creditAccountEvent.getAmount();
        currency = creditAccountEvent.getCurrency();
    }


    @CommandHandler
    public void handleChangeStatus(UpdateStatusCommand updateStatusCommand) {
        AggregateLifecycle.apply(new UpdateStatusEvent(
                updateStatusCommand.getAccountId(),
                updateStatusCommand.getStatus()
        ));
    }


    @EventSourcingHandler
    public void on(UpdateStatusEvent updateStatusEvent) {
        accountId = updateStatusEvent.getAccountId();
        status = updateStatusEvent.getStatus();
    }


}
