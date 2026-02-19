package me.asunamyadmin.bank.transactions.handler;

import java.time.LocalDateTime;

public record TransactionExceptionDTO(
        String title,
        StackTraceElement[] traceElements,
        LocalDateTime time
) {
}
