package me.asunamyadmin.bank.bank_account.service;

import java.math.BigDecimal;

public record AccountDTO(
        Integer userId,
        String accountNumber,
        BigDecimal balance,
        Currency currency,
        AccountType accountType,
        AccountStatus status
) {
}
