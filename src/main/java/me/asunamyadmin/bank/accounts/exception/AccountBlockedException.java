package me.asunamyadmin.bank.accounts.exception;

public class AccountBlockedException extends RuntimeException {
    public AccountBlockedException() {
        super("You have been blocked.");
    }
}
