package me.asunamyadmin.bank.bank_account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDTO(
        Integer userId,
        String accountNumber,
        BigDecimal balance,
        Currency currency,
        AccountType accountType,
        AccountStatus status,
        Boolean isBlocked,
        LocalDateTime createdAt
) {
}
