package com.enset.escqrs.events;

import com.enset.escqrs.enumirations.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Getter
public class UpdateStatusEvent {
    private String accountId;
    private AccountStatus status;
}
