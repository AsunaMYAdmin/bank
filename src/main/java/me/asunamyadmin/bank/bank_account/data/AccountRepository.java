package me.asunamyadmin.bank.bank_account.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    boolean existsByAccountNumber(String accountNumber);

    List<AccountEntity> findAllByUserId(Integer userId);

    Optional<AccountEntity> findByAccountNumber(String accountNumber);
}
