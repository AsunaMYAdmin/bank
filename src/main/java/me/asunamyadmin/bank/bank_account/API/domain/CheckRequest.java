package me.asunamyadmin.bank.bank_account.API.domain;

public record CheckRequest(
        String username,
        String accountNumber
) {
}
