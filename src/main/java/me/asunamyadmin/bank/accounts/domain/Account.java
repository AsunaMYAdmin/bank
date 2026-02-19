package me.asunamyadmin.bank.accounts.domain;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Account(
    Integer id,
    @NotNull
    Integer userId,
    @NotNull
    Integer account_number,
    @NotNull
    BigDecimal balance,
    @NotNull
    Integer version,
    LocalDateTime createdAt
) {
}
