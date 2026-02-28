package me.asunamyadmin.bank.profile.exception;

public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException() {
        super("The username is already taken!");
    }
}
