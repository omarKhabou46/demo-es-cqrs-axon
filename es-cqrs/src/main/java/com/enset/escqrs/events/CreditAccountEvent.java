package com.enset.escqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class CreditAccountEvent {
    private String accountId;
    private double amount;
    private String currency;
}
