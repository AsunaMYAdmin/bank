package me.asunamyadmin.bank.bank_profile.exception;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException() {
        super("Profile Not Found");
    }
}
