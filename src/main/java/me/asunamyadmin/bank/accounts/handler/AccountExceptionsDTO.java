package me.asunamyadmin.bank.accounts.handler;

import java.time.LocalDateTime;

public record AccountExceptionsDTO(
        String title,
        StackTraceElement[] message,
        LocalDateTime date
) {
}
