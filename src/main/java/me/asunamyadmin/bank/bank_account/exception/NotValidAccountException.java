package me.asunamyadmin.bank.bank_account.exception;

public class NotValidAccountException extends RuntimeException {
    public NotValidAccountException() {
        super("Account not valid");
    }
}
