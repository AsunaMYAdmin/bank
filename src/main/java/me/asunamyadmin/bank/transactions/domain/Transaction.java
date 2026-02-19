package me.asunamyadmin.bank.transactions.domain;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
        Integer id,
        @NotNull
        Integer fromAccountId,
        @NotNull
        Integer toAccountId,
        @NotNull
        BigDecimal amount,
        TransactionStatus status,
        LocalDateTime createdAt
) {
        public Transaction(BigDecimal amount, Integer toAccountId, Integer fromAccountId) {
                this(0, fromAccountId, toAccountId, amount, null, null);
        }
}
