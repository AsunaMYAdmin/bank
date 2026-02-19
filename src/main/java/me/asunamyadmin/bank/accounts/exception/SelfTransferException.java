package me.asunamyadmin.bank.accounts.exception;

public class SelfTransferException extends RuntimeException {
    public SelfTransferException() {
        super("You cannot make a transfer to yourself.");
    }
}
