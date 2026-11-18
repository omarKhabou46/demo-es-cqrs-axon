package com.enset.escqrs.commands.dtos;

public record AddNewAccountRequestDTO(double initialBalance, String currency) {
}
