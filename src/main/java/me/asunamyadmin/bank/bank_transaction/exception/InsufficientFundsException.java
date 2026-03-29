package me.asunamyadmin.bank.bank_transaction.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Недостаточно средств!");
    }
}
