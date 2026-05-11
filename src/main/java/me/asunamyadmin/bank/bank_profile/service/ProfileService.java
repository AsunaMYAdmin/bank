package me.asunamyadmin.bank.bank_profile.service;

import lombok.RequiredArgsConstructor;
import me.asunamyadmin.bank.bank_profile.data.BankProfileEntity;
import me.asunamyadmin.bank.bank_profile.data.BankProfileRepository;
import me.asunamyadmin.bank.bank_profile.exception.ProfileNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final BankProfileRepository bankProfileRepository;
    private final ProfileMapper profileMapper;

    public int getIdByName(String name) {
        BankProfileEntity bankProfileEntity = bankProfileRepository.findByUsername(name)
                .orElseThrow(ProfileNotFoundException::new);
        return bankProfileEntity.getId();
    }

    public ProfileDTO getDTOByName(String name) {
        return profileMapper.mapToDTO(
                bankProfileRepository.findByUsername(name).orElseThrow(ProfileNotFoundException::new));
    }
}
