package me.asunamyadmin.bank.bank_profile.service;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public record ProfileDTO (
        Integer id,
        String username,
        Status status,
        LocalDateTime createdAt
) {
}
