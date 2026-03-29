package me.asunamyadmin.bank.bank_profile.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankProfileRepository extends JpaRepository<BankProfileEntity, Integer> {
    Optional<BankProfileEntity> findByUsername(String username);
}
