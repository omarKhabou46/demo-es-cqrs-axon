package com.enset.escqrs.query.handlers;

import com.enset.escqrs.enumirations.OperationType;
import com.enset.escqrs.events.AddAccountEvent;
import com.enset.escqrs.events.CreditAccountEvent;
import com.enset.escqrs.events.DebitAccountEvent;
import com.enset.escqrs.events.UpdateStatusEvent;
import com.enset.escqrs.query.entity.Account;
import com.enset.escqrs.query.entity.AccountOperation;
import com.enset.escqrs.query.repository.AccountRepository;
import com.enset.escqrs.query.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountHandler {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    @EventHandler
    public void on(AddAccountEvent addAccountEvent, EventMessage eventMessage) {
        log.info("####### Query Side AddAccountEvent Recieved ########");
        Account account = Account.builder()
                .accountId(addAccountEvent.getAccountId())
                .balance(addAccountEvent.getBalance())
                .currency(addAccountEvent.getCurrency())
                .createdAt(eventMessage.getTimestamp())
                .status(addAccountEvent.getStatus())
                .build();
        accountRepository.save(account);
    }

    @EventHandler
    public void on(UpdateStatusEvent updateStatusEvent) {
        log.info("####### Query Side UpdateStatusEvent Recieved ########");
        Account account = accountRepository.findById(updateStatusEvent.getAccountId())
                .orElseThrow(() -> new RuntimeException("account not found"));
        account.setStatus(updateStatusEvent.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(CreditAccountEvent creditAccountEvent, EventMessage eventMessage) {
        log.info("####### Query Side CreditAccountEvent Recieved ########");
        Account account = accountRepository.findById(creditAccountEvent.getAccountId())
                .orElseThrow(() -> new RuntimeException("account not found"));
        account.setBalance(account.getBalance() + creditAccountEvent.getAmount());
        accountRepository.save(account);
        AccountOperation operation = AccountOperation.builder()
                .account(account)
                .type(OperationType.CREDIT)
                .amount(creditAccountEvent.getAmount())
                .currency(creditAccountEvent.getCurrency())
                .date(eventMessage.getTimestamp())
                .build();
        operationRepository.save(operation);
    }

    @EventHandler
    public void on(DebitAccountEvent debitAccountEvent, EventMessage eventMessage) {
        log.info("####### Query Side DebitAccountEvent Recieved ########");
        Account account = accountRepository.findById(debitAccountEvent.getAccountId())
                .orElseThrow(() -> new RuntimeException("account not found"));
        account.setBalance(account.getBalance() - debitAccountEvent.getAmount());
        accountRepository.save(account);
        AccountOperation operation = AccountOperation.builder()
                .account(account)
                .amount(debitAccountEvent.getAmount())
                .currency(debitAccountEvent.getCurrency())
                .type(OperationType.DEBIT)
                .date(eventMessage.getTimestamp())
                .build();
        operationRepository.save(operation);
    }
}
