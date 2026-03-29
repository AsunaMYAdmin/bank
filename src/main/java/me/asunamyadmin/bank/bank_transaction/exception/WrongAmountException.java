package me.asunamyadmin.bank.bank_transaction.exception;

public class WrongAmountException extends RuntimeException {
    public WrongAmountException() {
        super("Wrong Amount");
    }
}
