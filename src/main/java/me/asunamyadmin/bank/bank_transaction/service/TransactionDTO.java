package me.asunamyadmin.bank.bank_transaction.service;

import me.asunamyadmin.bank.bank_account.service.Currency;

import java.math.BigDecimal;

public record TransactionDTO(
        Integer fromAccountId,
        Integer toAccountId,
        BigDecimal amount,
        Currency currency,
        TransactionType type,
        TransactionStatus status
) {
}
