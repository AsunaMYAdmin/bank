package me.asunamyadmin.bank.bank_profile.service;

import java.time.LocalDateTime;

public record ProfileDTO (
        Integer id,
        String username,
        Status status,
        LocalDateTime createdAt
) {
}
