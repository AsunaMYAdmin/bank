package me.asunamyadmin.bank.accounts.exception;

public class AccountHasAlreadyBeenBlocked extends RuntimeException {
    public AccountHasAlreadyBeenBlocked() {
        super("Account has already been blocked.");
    }
}
