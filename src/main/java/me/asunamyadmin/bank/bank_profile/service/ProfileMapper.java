package me.asunamyadmin.bank.bank_profile.service;

import me.asunamyadmin.bank.bank_profile.data.BankProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public ProfileDTO mapToDTO(BankProfileEntity profile) {
        return new ProfileDTO(profile.getId(), profile.getUsername(), profile.getStatus(), profile.getCreatedAt());
    }
}
