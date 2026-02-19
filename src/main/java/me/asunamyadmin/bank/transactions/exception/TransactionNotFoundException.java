package me.asunamyadmin.bank.transactions.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
        super("Transaction not found!");
    }
}
