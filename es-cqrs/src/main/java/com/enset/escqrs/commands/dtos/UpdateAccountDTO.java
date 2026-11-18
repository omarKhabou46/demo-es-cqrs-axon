package com.enset.escqrs.commands.dtos;

import com.enset.escqrs.enumirations.AccountStatus;

public record UpdateAccountDTO(String accountId, AccountStatus status) {
}
