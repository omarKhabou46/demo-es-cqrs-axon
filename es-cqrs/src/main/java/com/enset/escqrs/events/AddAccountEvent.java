package com.enset.escqrs.events;

import com.enset.escqrs.enumirations.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddAccountEvent {
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;
}
