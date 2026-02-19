package me.asunamyadmin.bank.accounts.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("There are insufficient funds in the account!");
    }
}
