package me.asunamyadmin.bank.bank_account.service;

import java.security.SecureRandom;

public class AccountGenerator {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateAccount(AccountType type) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        String acc = sb.toString();

        return type.name() + "-" +
                acc.substring(0, 4) + "-" +
                acc.substring(4, 8) + "-" +
                acc.substring(8, 12) + "-" +
                acc.substring(12, 16);
    }
}
