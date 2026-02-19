package me.asunamyadmin.bank.accounts.domain;

public record Transfer(
        int fromId,
        int toId
) {
}
