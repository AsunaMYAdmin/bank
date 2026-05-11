package me.asunamyadmin.bank.bank_account.exception;

public class AccountTypeAlreadyExistsException extends RuntimeException {
    public AccountTypeAlreadyExistsException() {
        super("Аккаунт с таким типом счёта уже создан.");
    }
}
