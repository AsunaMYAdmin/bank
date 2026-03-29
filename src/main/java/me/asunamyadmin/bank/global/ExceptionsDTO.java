package me.asunamyadmin.bank.global;

import java.time.LocalDateTime;

public record ExceptionsDTO(
        String title,
        LocalDateTime errorTime
) {
}
