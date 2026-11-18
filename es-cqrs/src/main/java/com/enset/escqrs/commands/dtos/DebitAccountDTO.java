package com.enset.escqrs.commands.dtos;

public record DebitAccountDTO(String accountId, double amount, String currency) {
}
