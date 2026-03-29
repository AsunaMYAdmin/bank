package me.asunamyadmin.bank.bank_account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.asunamyadmin.bank.bank_account.data.AccountEntity;
import me.asunamyadmin.bank.bank_account.data.AccountRepository;
import me.asunamyadmin.bank.bank_account.exception.AccountNotFoundException;
import me.asunamyadmin.bank.bank_transaction.exception.InsufficientFundsException;
import me.asunamyadmin.bank.bank_transaction.exception.WrongAmountException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;

    public List<AccountDTO> getAllAccountsById(Integer id) {
        return repository.findAllByUserId(id).stream()
                .map(mapper::getAccountFromEntity)
                .toList();
    }

    @Transactional
    public void createAccount(AccountDTO accountDTO) {
        AccountEntity entity = new AccountEntity();
        entity.setUserId(accountDTO.userId());
        do {
            entity.setAccountNumber(AccountGenerator.generateAccount(accountDTO.accountType()));
        } while (repository.existsByAccountNumber(entity.getAccountNumber()));
        entity.setBalance(BigDecimal.ZERO);
        entity.setCurrency(accountDTO.currency());
        entity.setAccountType(accountDTO.accountType());
        entity.setStatus(AccountStatus.ACTIVE);
        repository.save(entity);
    }

    @Transactional
    public void deleteAccount(Integer id) {
        AccountEntity entity = repository.findByUserId(id).orElseThrow(AccountNotFoundException::new);
        repository.delete(entity);
    }

    @Transactional
    public void withDraw(Integer id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new WrongAmountException();
        }
        AccountEntity entity = repository.findByUserId(id).orElseThrow(AccountNotFoundException::new);
        if (entity.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }
        entity.setBalance(entity.getBalance().subtract(amount));
        repository.save(entity);
    }

    @Transactional
    public void replenishment(Integer id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new WrongAmountException();
        }
        AccountEntity entity = repository.findByUserId(id).orElseThrow(AccountNotFoundException::new);
        entity.setBalance(entity.getBalance().add(amount));
        repository.save(entity);
    }
}
