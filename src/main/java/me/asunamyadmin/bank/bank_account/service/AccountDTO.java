package me.asunamyadmin.bank.bank_account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccountDTO that = (AccountDTO) obj;
        return Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
